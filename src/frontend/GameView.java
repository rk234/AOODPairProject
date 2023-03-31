package frontend;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import backend.Level;
import backend.Planet;
import rendering.Camera;
import rendering.Renderer;
import utils.InputHandler;
import utils.Vector2;
import backend.Entity;
import javax.swing.Timer;

public class GameView extends JPanel {
    //TODO: maybe implement a fastforward boolean for gameview
    private Level level;
    private Camera cam;
    private float PHYSICS_STEP = 1/15f;
    private final int FORRECAST_STEPS = 10000;
    private int FPS = 0;

    private BufferedImage framebuffer;
    private int prevHeight, prevWidth;

    public GameView(Level level) {
        this.level = level;
        cam = new Camera(level.getRocket().getPosition(), 1f);
        setPreferredSize(new Dimension(800,800));
        startLevel();
        setFocusable(true);
        requestFocus();
        addKeyListener(InputHandler.main);
    }

    public void startLevel() {
        Timer loopTimer = new Timer(1000/30, new ActionListener() {
            private long lastTime = System.currentTimeMillis();
            private long elapsedTime = 0;
            @Override
            public void actionPerformed(ActionEvent event) {
                repaint();
                long dt = System.currentTimeMillis()-lastTime;

                for(Entity e : level.getEntities()) {
                    e.update(dt/1000f, level.getEntities());
                    e.calculatePhysics(dt/1000f);
                }

                level.getRocket().update(dt/1000f, level.getEntities());
                level.getRocket().calculatePhysics(PHYSICS_STEP);

                if(InputHandler.main.isKeyPressed(KeyEvent.VK_SHIFT)) {
                    cam.setZoom(cam.getZoom()*(1-dt/1000f));
                }
                if(InputHandler.main.isKeyPressed(KeyEvent.VK_ENTER)) {
                    cam.setZoom(cam.getZoom()*(1+dt/1000f));
                }

                if(level.getObjective().isFailed()) {
                    System.out.println("Obj Failed");
                } else {
                    if(level.getObjective().checkCompleted(level.getRocket())) {
                        //Complete level
                        System.out.println("Level completed");
                    }
                }

                cam.setPosition(level.getRocket().getPosition());

                elapsedTime+=dt;
                lastTime = System.currentTimeMillis();
                FPS = (int) (1000/dt);
            }
        });
        loopTimer.start();
    }


    public void paintComponent(Graphics g) {
        if(prevWidth != getWidth() || prevHeight != getHeight())
            framebuffer = createFramebuffer(getWidth()*2, getHeight()*2);
        prevHeight = getHeight();
        prevWidth = getWidth();

        Graphics2D g2d = (Graphics2D) framebuffer.getGraphics();
        g2d.setBackground(Color.BLACK);

        Renderer r = new Renderer(cam, getWidth()*2, getHeight()*2, g2d);
        r.clear();

        drawForecast(r);

        for(Entity e : level.getEntities()) {
            e.draw(r);
        }
        level.getRocket().draw(r);

        r.drawLine(level.getRocket().getPosition(), level.getRocket().getPosition().add(level.getRocket().direction().scale(100)), Color.green);
        drawUI(g2d);
        
        g.drawImage(framebuffer, 0, 0,getWidth(), getHeight(), null);
    }

    public void drawUI(Graphics2D g) {
        g.setTransform(new AffineTransform());
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        g.drawString("FPS: " + FPS, 8, 40);
        g.drawString("Velocity: " + level.getRocket().getVelocity().magnitude(), 8, 80);
        g.setColor(Color.blue);
        g.fillRect(0,  (getHeight()*2)-200, 50, getHeight()*2);
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
}
