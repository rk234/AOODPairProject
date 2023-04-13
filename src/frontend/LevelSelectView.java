package frontend;

import javax.swing.JPanel;

import backend.Main;
import backend.LevelManager;
import backend.Level;

import javax.swing.JButton;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LevelSelectView extends JPanel implements ActionListener{
    //private JButton[] levelButtons;

    public LevelSelectView(Level[] levels) {
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setLayout(new GridLayout(5,0, 10, 10));
        for (int i = 0; i < levels.length; i++) {
            JButton btn = new JButton("Level " + i);
            btn.addActionListener(this);
            this.add(btn);
        }
        setPreferredSize(new Dimension(800, 800));
    }
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton)e.getSource();
        String text = btn.getText();
        text = text.split(" ")[1];
        int level = Integer.parseInt(text);
        Main.changeView("GameView", LevelManager.getLevel(level));
    }
}