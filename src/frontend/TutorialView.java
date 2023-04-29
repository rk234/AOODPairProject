package frontend;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.io.File;

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
        menuButton = new JButton("Menu");
        leftButton = new JButton("Back");
        rightButton = new JButton("Forward");
        buttonPanel.add(menuButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(leftButton);
        buttonPanel.add(rightButton);

        add(imagePanel);
        add(buttonPanel);
    }
    public void loadImages() {
        images = new BufferedImage[new File("assets/tutorial").listFiles().length];
        for (int i = 0; i < images.length; i++) {
            try {
                images[i] = ImageIO.read(new File("assets/tutorial/slide"+i+".png"));
            } catch (Exception ex) {
                System.out.println("Couldn't load tutorial image");
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton)e.getSource();
        String text = btn.getText();
        if (text.equals("Back")) {
            currentSlide--;
            if (currentSlide == 0) {
                //TODO: finish this
                //backButton.setEnabled(false);
            }
        } else if (text.equals("Foward")) {
            currentSlide++;
        }
    }
}