package frontend;

import javax.swing.JPanel;

import backend.Main;
import backend.LevelManager;
import backend.Level;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LevelSelectView extends JPanel implements ActionListener{
    //private JButton[] levelButtons;

    private int pageIndex = 0;
    private int levelsPerPage = 5;
    private Level[] levels;
    private JPanel levelBtns;

    private JButton forwardButton;
    private JButton backButton;

    public LevelSelectView(Level[] levels) {
        this.levels = levels;
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        levelBtns = new JPanel();
        levelBtns.setLayout(new GridLayout(5,0, 10, 10));
        
        JLabel title = new JLabel("Level Select");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        title.setAlignmentX(CENTER_ALIGNMENT);
        add(title);
        add(Box.createRigidArea(new Dimension(0, 10)));
        
        JPanel navigation = new JPanel();
        navigation.setLayout(new BoxLayout(navigation, BoxLayout.X_AXIS));

        JButton menuBtn = new JButton("Return to Menu");
        menuBtn.addActionListener(this);
        navigation.add(menuBtn);

        navigation.add(Box.createHorizontalGlue());
        backButton = new JButton("Back");
        backButton.setEnabled(false);
        backButton.addActionListener(this);
        forwardButton = new JButton("Forward");
        forwardButton.addActionListener(this);
        navigation.add(backButton);
        navigation.add(forwardButton);
        updatePage();
        add(levelBtns);
        add(navigation);
        setPreferredSize(new Dimension(800, 800));
    }
    public void updatePage() {
        levelBtns.removeAll();
        int completedCounter = pageIndex;
        while(levels[completedCounter].isComplete())
            completedCounter++;
        for (int i = pageIndex; i < Math.min(pageIndex+levelsPerPage, levels.length); i++) {
            JButton btn = new JButton("Level " + (i+1));
            btn.addActionListener(this);
            if (i > completedCounter) {
                btn.setEnabled(false);
            }
            levelBtns.add(btn);
        }

        repaint();
        revalidate();

        if(pageIndex+levelsPerPage < levels.length) {
            forwardButton.setEnabled(true);
        } else {
            forwardButton.setEnabled(false);
        }

        if(pageIndex-levelsPerPage >= 0) {
            backButton.setEnabled(true);
        } else {
            backButton.setEnabled(false);
        }
    }
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton)e.getSource();
        String text = btn.getText();
        if (text.equals("Return to Menu")) {
            Main.changeView("MenuView");
        } else if(text.equals("Forward")) {
            if(pageIndex+levelsPerPage < levels.length) {
                pageIndex+=levelsPerPage;
                updatePage();
            }
        } else if (text.equals("Back")) {
            if(pageIndex-levelsPerPage >= 0) {
                pageIndex-=levelsPerPage;
                updatePage();
            }
        } else {
            text = text.split(" ")[1];
            int level = Integer.parseInt(text)-1;
            Main.changeView("GameView", LevelManager.getLevel(level));
        }
    }
}