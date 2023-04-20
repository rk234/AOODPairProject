package frontend;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
    
import backend.Main;
import backend.Level;
import backend.LevelManager;

public class EndPanel extends JPanel {
    private Level level;
    public void paint(Graphics g) {
        super.paint(g);
        super.paintChildren(g);
    }
    public EndPanel(Level l, boolean failed) {
        level = l;
        setBackground(new Color(255, 255, 255, 255));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton continueButton = new JButton("Continue");
        if (!failed) {
            continueButton.setText("Next Level");
        }
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(failed) {
                    Main.changeView("GameView", LevelManager.getLevel(level.getID()));
                } else {
                    Main.changeView("GameView", LevelManager.getLevel(level.getID()+1));
                }
            }
        });
        continueButton.setAlignmentX(CENTER_ALIGNMENT);
        JButton levelButton = new JButton("Level Select");
        levelButton.setAlignmentX(CENTER_ALIGNMENT);
        levelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.changeView("LevelSelectView");
            }
        });

        JLabel msgLabel = new JLabel("Level Failed, " + level.getObjective().getFailMessage());
        if(!failed) {
            msgLabel.setText("Level Completed!");
        }
        
        msgLabel.setAlignmentX(CENTER_ALIGNMENT);
        msgLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        this.add(msgLabel);
        this.add(continueButton);
        this.add(levelButton);
    }
    public Dimension getPreferredSize() {
        return new Dimension(200,200);
    }
}