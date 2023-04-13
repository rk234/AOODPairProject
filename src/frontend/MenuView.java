package frontend;

import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import backend.Main;

public class MenuView extends JPanel implements ActionListener{
    private JButton playButton;

    public MenuView() {
        setPreferredSize(new Dimension(800,800));
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        this.add(Box.createRigidArea(new Dimension(0,200)));
        //this.add(Box.createVerticalGlue(300));
        JButton button = new JButton("Play");
        button.setAlignmentX(CENTER_ALIGNMENT);

        button.addActionListener(this);
        this.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Main.changeView("LevelSelectView");
    }
}