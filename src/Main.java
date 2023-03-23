import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import backend.Planet;
import backend.Rocket;
import rendering.Camera;
import rendering.Renderer;
import utils.TextureManager;
import utils.Vector2;

public class Main {
    public static void main(String[] args) {
        BufferedImage planetImg = TextureManager.main.getTexture("planet0");
       
        Planet test = new Planet(new Vector2(), 100, 100, planetImg);    
        Rocket rocket = new Rocket(new Vector2(0, 100), 10, TextureManager.main.getTexture("rocket"));
        JFrame frame = new JFrame();
        JPanel p = new JPanel() {
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setBackground(Color.black);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Renderer r = new Renderer(new Camera(new Vector2(), 2), getWidth(), getHeight(), g2d);
                r.clear();
                test.draw(r);
                rocket.draw(r);
            }
        };

        p.setPreferredSize(new Dimension((int) (720*(16/9f)), 720));

        frame.setContentPane(p);
        frame.pack();
        frame.setVisible(true);
    }
}