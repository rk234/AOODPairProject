import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
    public static void main(String[] args) {
        //Planet test = new Planet(new Vector2(), 100, 10, );        

        JFrame frame = new JFrame();
        JPanel p = new JPanel() {
            public void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.rotate(Math.PI/4, 200, 200);
                g2d.translate(200, 200);
                g2d.scale(10, 10);
                
                g2d.setColor(Color.red);
                g2d.fillRect(-10, -10, 20, 20);
            }
        };
        p.setPreferredSize(new Dimension(400, 400));

        frame.setContentPane(p);
        frame.pack();
        frame.setVisible(true);
    }
}