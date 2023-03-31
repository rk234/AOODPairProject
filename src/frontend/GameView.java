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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.BLACK);
        Renderer r = new Renderer(cam, getWidth(), getHeight(), g2d);
        r.clear();

        drawForecast(r);

        for(Entity e : level.getEntities()) {
            e.draw(r);
        }
        level.getRocket().draw(r);

        r.drawLine(level.getRocket().getPosition(), level.getRocket().getPosition().add(level.getRocket().direction().scale(100)), Color.green);

        g2d.setTransform(new AffineTransform());
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        g2d.drawString("FPS: " + FPS, 8, 40);
        g2d.drawString("Velocity: " + level.getRocket().getVelocity().magnitude(), 8, 80);
        //g2d.drawString("Altitude " + level.getRocket().altitude(null), 8, 80);

        drawUI(g2d);
    }

    public void drawUI(Graphics2D g) {
        g.setTransform(AffineTransform.getTranslateInstance(2, 2));
        g.setColor(Color.blue);
        g.fillRect(0,  (getHeight()*2)-200, 50, getHeight()*2);
        g.setColor(Color.white);
        g.drawRect(0, 0, 50, 50);
        g.drawRect(0, 800, 50, 50);
        g.drawRect(800, 0, 50, 50);
        g.drawRect(getHeight(), getHeight(), 50, 50);
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
}
