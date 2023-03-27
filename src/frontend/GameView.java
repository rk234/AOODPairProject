package frontend;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private Level level;
    private Camera cam;
    private float PHYSICS_STEP = 1/15f;

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

                //rotate the rocket according to velocity
                /*float angle = (float) Math.toDegrees(Math.atan(level.getRocket().getVelocity().getY()/level.getRocket().getVelocity().getX()));
                if((level.getRocket().getVelocity().getX() < 0)) angle = 180+angle;
                level.getRocket().setRotation(90-angle);*/
                
                cam.setPosition(level.getRocket().getPosition());

                elapsedTime+=dt;
                lastTime = System.currentTimeMillis();

                if(elapsedTime > 1000) {
                    System.out.println("FPS: " + (1000/dt));
                    elapsedTime = 0;
                }
            }
        });
        loopTimer.start();
    }


    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(Color.BLACK);
        Renderer r = new Renderer(cam, getWidth(), getHeight(), g2d);
        r.clear();
        for(Entity e : level.getEntities()) {
            e.draw(r);
        }
        level.getRocket().draw(r);

        r.debugDrawLine(level.getRocket().getPosition(), level.getRocket().getPosition().add(level.getRocket().direction().scale(100)), Color.green);

        Vector2[] fcst = level.getRocket().forecast(PHYSICS_STEP, 500, level.getEntities());
        for(int i = 0; i < fcst.length-1; i++) {
            r.debugDrawLine(fcst[i], fcst[i+1], Color.GRAY);
        }
    }
}
