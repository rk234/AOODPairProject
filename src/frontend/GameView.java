package frontend;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

import backend.AltitudeObjective;
import backend.Entity;
import backend.LandObjective;
import backend.Level;
import backend.LevelManager;
import backend.Main;
import backend.OrbitObjective;
import backend.Planet;
import rendering.Camera;
import rendering.Renderer;
import utils.InputHandler;
import utils.Vector2;

public class GameView extends JPanel {
    //TODO: maybe implement a fastforward boolean for gameview
    //TODO: manage rocket visibility when very zoomed out
    private Level level;
    private Camera cam;
    private float PHYSICS_STEP = 1/15f;
    private final int FORRECAST_STEPS = 10000;
    private int timeScale = 1;
    private HashSet<String> planetsVisited;

    private BufferedImage framebuffer;
    private int prevHeight, prevWidth;

    private EndPanel panel;
    private HeaderPanel header;

    public GameView(Level level) {
        planetsVisited = new HashSet<String>();
        setDoubleBuffered(true);
        this.level = level;
        cam = new Camera(level.getRocket().getPosition(), 3f);
        setPreferredSize(new Dimension(800,800));
        setLayout(null);
        startLevel();
        setFocusable(true);
        requestFocus();
        setBackground(new Color(0,0,0,0));
        setOpaque(false);

        header = new HeaderPanel(level.getObjective(), new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JRadioButton button = (JRadioButton)(e.getSource());
                int ind = 0;
                String modText = "";
                while (button.getText().charAt(ind) != 'x') {
                    modText += button.getText().charAt(ind);
                    ind++;
                }
                timeScale = Integer.parseInt(modText);
                Main.requestFocus();
            }
        });
        add(header);

        //addKeyListener(InputHandler.main);
    }

    public boolean isOptimizedDrawingEnabled() {
        return true;
    }

    public void startLevel() {
        cam.setZoom(level.getDefaultZoom());
        Timer loopTimer = new Timer(1000/30, null);

        loopTimer.addActionListener(new ActionListener() {
            private long lastTime = System.currentTimeMillis();
            private float elapsedSinceComplete = 0;
            @Override
            public void actionPerformed(ActionEvent event) {
                repaint();                
                //header.setFps(FPS);
                Planet currentPlanet = null;
                //header.altitude(level.getRocket().altitude(null));
                long dt = System.currentTimeMillis()-lastTime;

                for(Entity e : level.getEntities()) {
                    for(int i = 0; i < timeScale; i++) {
                        e.update(dt/1000f, level.getEntities());
                        e.calculatePhysics(PHYSICS_STEP);
                    }

                    if(e instanceof Planet) {
                        if(((Planet) e).inInfluence(level.getRocket().getPosition())) {
                            currentPlanet = (Planet) e;
                            planetsVisited.add(currentPlanet.getName());
                        }
                    }
                }

                for(int i = 0; i < timeScale; i++) {
                    level.getRocket().update(dt/1000f, level.getEntities());
                    level.getRocket().calculatePhysics(PHYSICS_STEP);
                }

                if(InputHandler.main.isKeyPressed(KeyEvent.VK_SHIFT)) {
                    cam.setZoom(cam.getZoom()*(1-dt/1000f));
                }
                if(InputHandler.main.isKeyPressed(KeyEvent.VK_ENTER)) {
                    cam.setZoom(cam.getZoom()*(1+dt/1000f));
                }

                if(level.getObjective().isFailed()) {
                    System.out.println("Obj Failed");
                    showLevelFailPanel();
                    loopTimer.stop();
                } else {
                    if(planetsVisited.size() == level.getEntities().length) {
                        if(level.getObjective().checkCompleted(level.getRocket())) {
                            elapsedSinceComplete+=(dt/1000f);
                            if(level.getObjective() instanceof AltitudeObjective) elapsedSinceComplete=2;
                            header.updateObjective(true);
                        } else {
                            elapsedSinceComplete = 0;
                            header.updateObjective(false);
                        }
                    }
                }

                if(elapsedSinceComplete >= 2) {
                    try {
                        LevelManager.setComplete(level.getID(), true);
                    } catch (Exception ex) {
                    
                    }
                    showLevelCompletePanel();
                    loopTimer.stop();
                }

                cam.setPosition(level.getRocket().getPosition());
                lastTime = System.currentTimeMillis();

                header.setVelocity(level.getRocket().getVelocity().magnitude());
                if (currentPlanet == null) {
                    header.setAltitudeText("Altitude: N/A");
                } else {
                    header.setAltitude(level.getRocket().altitude(currentPlanet));
                }
            }
        });
        loopTimer.start();
    }


    public void paintComponent(Graphics g) {
        header.setBounds(0, 0, getWidth(), 60);
        if(prevWidth != getWidth() || prevHeight != getHeight())
            framebuffer = createFramebuffer(getWidth()*2, getHeight()*2);
        prevHeight = getHeight();
        prevWidth = getWidth();

        if(panel != null) {
            panel.setBounds(getWidth()/4, getHeight()/8, getWidth()/2, getHeight()/8);
        }

        Graphics2D g2d = (Graphics2D) framebuffer.getGraphics();
        g2d.setBackground(Color.BLACK);

        Renderer r = new Renderer(cam, getWidth()*2, getHeight()*2, g2d);
        r.clear();

        drawForecast(r);
        if(level.getObjective() instanceof OrbitObjective) {
            OrbitObjective obj = (OrbitObjective) level.getObjective();
            r.drawOval(obj.getTargetPlanet().getPosition(), new Vector2((obj.getMinimumAltitude()+obj.getTargetPlanet().getRadius())*2), Color.GREEN, new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
            0, new float[]{10}, 0));
        }

        for(Entity e : level.getEntities()) {
            e.draw(r);
        }
        float zoom = cam.getZoom();
        if (zoom > 1) {
            level.getRocket().draw(r);
        } else {
            r.drawTriangle(level.getRocket().getPosition(), new Vector2((1/zoom)*30), level.getRocket().getRotation(), Color.RED);
        }

        //r.drawLine(level.getRocket().getPosition(), level.getRocket().getPosition().add(level.getRocket().direction().scale(100)), Color.green);
        drawUI(g2d);
        
        g.drawImage(framebuffer, 0, 0,getWidth(), getHeight(), null);
    }

    public void drawUI(Graphics2D g) {
        g.setTransform(new AffineTransform());
        g.setColor(Color.WHITE);
        /*
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        g.drawString("FPS: " + FPS, 8, 40);
        g.drawString("Velocity: " + level.getRocket().getVelocity().magnitude(), 8, 80);
        */
        drawFuelBar(g);
    }

    public void drawFuelBar(Graphics2D g) {
        int height = 400;
        int width = 90;

        g.setColor(Color.blue);
        g.fillRect(0,  (getHeight()*2)-height, width, getHeight()*2);
        g.setColor(Color.red);
        int padding = 10;
        float fuelRatio = level.getRocket().getFuel()/level.getRocket().getInitialFuel();
        int fuelBoxY = (int) ((getHeight()*2)-((height-padding)*fuelRatio));
        g.fillRect(padding,  fuelBoxY, width-(padding*2), getHeight()*2);
    }

    public void showLevelFailPanel() {
        System.out.println("showing fail");
        panel = new EndPanel(level,true);
        panel.setBounds(getWidth()/4, getHeight()/8, getWidth()/2, getHeight()/6);
        add(panel);
        panel.invalidate();
        Main.windowRepaint();
    }
    public void showLevelCompletePanel() {
        System.out.println("showing win");
        panel = new EndPanel(level,false);
        panel.setBounds(getWidth()/4, getHeight()/8, getWidth()/2, getHeight()/6);
        add(panel);
        panel.invalidate();
        Main.windowRepaint();
    }

    public void drawForecast(Renderer r) {
        Vector2[] fcst = level.getRocket().forecast(PHYSICS_STEP, FORRECAST_STEPS, level.getEntities());
        Color c = Color.GRAY;

        if(fcst[FORRECAST_STEPS-1] == null) c = Color.RED;
        
        r.drawLine(level.getRocket().getPosition(), fcst[0], c);
        for(int i = 0; i < fcst.length-1; i++) {
            if(fcst[i] != null && fcst[i+1] != null)
                r.drawLine(fcst[i], fcst[i+1], c);
        }
    }

    public BufferedImage createFramebuffer(int width, int height) {
        return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    
    // public static void main(String[] args) {
    //     JFrame temp = new JFrame();
    //     JPanel panel = new JPanel();
    //     panel.add(new FailPanel(null));
    //     temp.add(panel);
    //     temp.setVisible(true);
    //     temp.pack();
    // }
}
