package frontend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import backend.Main;

public class MenuView extends JPanel implements ActionListener {
    private Particle[] particles = new Particle[150];
    private float elapsedTime = 0;
    private float dt = 0;

    public MenuView() {
        setPreferredSize(new Dimension(800, 800));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // this.add(Box.createVerticalGlue(300));
        JButton playBtn = new JButton("Play");
        playBtn.setAlignmentX(CENTER_ALIGNMENT);
        playBtn.setMaximumSize(new Dimension(200,70));


        playBtn.addActionListener(this);
        
        JLabel title = new JLabel("Unnamed Rocket Game");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setForeground(Color.WHITE);

        JButton tutorialBtn = new JButton("Tutorial");
        tutorialBtn.addActionListener(this);
        tutorialBtn.setAlignmentX(CENTER_ALIGNMENT);
        tutorialBtn.setMaximumSize(new Dimension(200,50));


        JButton attributionBtn = new JButton("Attribution");
        attributionBtn.setAlignmentX(CENTER_ALIGNMENT);
        attributionBtn.setMaximumSize(new Dimension(200,50));
        attributionBtn.addActionListener(this);

        this.add(title);
        this.add(Box.createRigidArea(new Dimension(0, 200)));
        this.add(playBtn);
        this.add(tutorialBtn);
        this.add(attributionBtn);
        setBackground(Color.BLACK);
        drawAnimation();
    }

    public void drawAnimation() {
        Timer loopTimer = new Timer(1000 / 30, null);

        loopTimer.addActionListener(new ActionListener() {
            private long lastTime = System.currentTimeMillis();
            @Override
            public void actionPerformed(ActionEvent e) {
                dt = (System.currentTimeMillis()-lastTime)/1000f;
                /*
                 * generate
                 * render
                 * remove
                 */
                repaint();
                lastTime = System.currentTimeMillis();
            }
        });
        loopTimer.start();
    }

    public void paintComponent(Graphics g) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < particles.length; i++) {
            if(particles[i] == null) {
                if(elapsedTime / 0.01f > 1) {
                    particles[i] = createParticle();
                    elapsedTime = 0;
                }
            } else if(particles[i].getPosition().getX() > 0 && particles[i].getPosition().getY() > 0 &&
            particles[i].getPosition().getX() < getWidth() && particles[i].getPosition().getY() < getHeight()) {
                particles[i].draw(g);
                particles[i].step(dt);
            } else {
                particles[i] = createParticle();
            }
        }

        elapsedTime+=dt;
    }

    private Particle createParticle() {
        float magnitude = (float) (Math.random() * 200) + 300;
        Particle particle = new Particle(magnitude, getWidth(), getHeight());
        return particle;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton)e.getSource();
        String text = btn.getText();
        switch (text) {
            case "Play":
                Main.changeView("LevelSelectView");
                break;
            case "Tutorial":
                Main.changeView("TutorialView");
                break;
            case "Attribution":
                JFrame popup = new JFrame("Attribution");
                popup.setAlwaysOnTop(true);
                popup.setContentPane(new AttributionPanel());
                popup.pack();
                popup.setVisible(true);
                break;
        }
    }
}