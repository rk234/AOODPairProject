package frontend;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import backend.Level;
import rendering.Camera;
import rendering.Renderer;
import utils.Vector2;
import backend.Entity;
import javax.swing.Timer;

public class GameView extends JPanel {
    private Level level;
    private Camera cam;

    public GameView(Level level) {
        this.level = level;
        cam = new Camera(level.getRocket().getPosition(), 1);
        setPreferredSize(new Dimension(800,800));
    }

    public void startLevel() {
        //level.getRocket().addForce(new Vector2(0,1));
        Timer loopTimer = new Timer(1000/60, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Renderer r = new Renderer(cam, getWidth(), getHeight(), g2d);
            
        for(Entity e : level.getEntities()) {
            e.draw(r);
        }
        level.getRocket().draw(r);
    }
}
