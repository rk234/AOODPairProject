package frontend;

import java.awt.Graphics;
import javax.swing.JPanel;

import backend.Main;

import java.io.File;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class TutorialView extends JPanel implements ActionListener {
    private int currentSlide = 0;
    private ImagePanel imagePanel;
    private JPanel buttonPanel;
    private BufferedImage[] images;
    private JButton menuButton;
    private JButton leftButton;
    private JButton rightButton;
    public TutorialView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        loadImages();
        imagePanel = new ImagePanel(images[0]);
        imagePanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        menuButton = new JButton("Return to Menu");
        menuButton.addActionListener(this);
        leftButton = new JButton("Back");
        leftButton.addActionListener(this);
        leftButton.setEnabled(false);
        rightButton = new JButton("Next");
        rightButton.addActionListener(this);
        buttonPanel.add(menuButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(leftButton);
        buttonPanel.add(rightButton);

        add(imagePanel);
        add(buttonPanel);
    }
    public void loadImages() {
        images = new BufferedImage[6];
        for (int i = 0; i < images.length; i++) {
            try {
                images[i] = ImageIO.read(new File("assets/tutorial/slide"+i+".png"));
            } catch (Exception ex) {
                System.err.println("Couldn't load tutorial image");
            }
        }
    }
    class ImagePanel extends JPanel {
        private BufferedImage img;
        public ImagePanel(BufferedImage img) {
            this.img = img;
        }

        public void paintComponent(Graphics g) {
            int size = Math.min(getWidth(), getHeight());
            g.drawImage(img, (getWidth()-size)/2, (getHeight()-size)/2, size, size, null);
        }

        public void setImage(BufferedImage img) {
            this.img = img;
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton)e.getSource();
        String text = btn.getText();
        if (text.equals("Back")) {
            if (currentSlide > 0) {
                currentSlide--;
            }
        } else if (text.equals("Next")) {
            if (currentSlide < images.length-1) {
                currentSlide++;
            }
        } else if (text.equals("Return to Menu")) {
            Main.changeView("MenuView");
        }

        if (currentSlide == 0) {
            leftButton.setEnabled(false);
        } else {
            leftButton.setEnabled(true);
        }
        if (currentSlide >= images.length-1) {
            rightButton.setEnabled(false);
        } else {
            rightButton.setEnabled(true);
        }
        imagePanel.setImage(images[currentSlide]);
    }
}