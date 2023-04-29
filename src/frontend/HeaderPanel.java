package frontend;

import backend.AltitudeObjective;
import backend.LandObjective;
import backend.Main;
import backend.Objective;
import backend.OrbitObjective;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HeaderPanel extends JPanel{
    private JLabel fpsLbl;
    private JLabel velocityLbl;
    private JLabel altitudeLbl;
    private JLabel objectiveLbl;
    
    private ActionListener fastForwardListener;
    private JPanel row1;
    private JPanel row2;
    private Objective obj;

    public HeaderPanel(Objective objective, ActionListener fastForwardListener) {
        this.fastForwardListener = fastForwardListener;
        this.obj = objective;
        setLayout(new GridLayout(0, 1, 0, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
       
        fpsLbl = new JLabel();
        velocityLbl = new JLabel();
        altitudeLbl = new JLabel();
        objectiveLbl = new JLabel();
        objectiveLbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, objectiveLbl.getFont().getSize()));

        if (objective instanceof LandObjective) {
            LandObjective obj = (LandObjective) objective;
            objectiveLbl.setText("Objective: Land on " + obj.getPlanet().getName());
        } else if (objective instanceof OrbitObjective) {
            OrbitObjective obj = (OrbitObjective) objective;
            objectiveLbl.setText("Objective: Orbit around " + obj.getPlanet().getName() + " at a minimum altitude of " + obj.getMinimumAltitude());
        } else if (objective instanceof AltitudeObjective) {
            AltitudeObjective obj = (AltitudeObjective) objective;
            objectiveLbl.setText("Objective: Reach an altitude of " + obj.altitude() + " above " + obj.getPlanet().getName());
        }

        row1.add(objectiveLbl);
        row1.add(Box.createHorizontalGlue());
        row1.add(fpsLbl);
        row1.add(Box.createRigidArea(new Dimension(5, 0)));
        row1.add(velocityLbl);
        row1.add(Box.createRigidArea(new Dimension(5, 0)));
        row1.add(altitudeLbl);

        row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        row2.add(new JLabel("Time scale: "));
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < 5; i++) {
            int[] mods = {1,3,5,10,50};
            String text = (mods[i]) + "x";
            JRadioButton btn = new JRadioButton(text);
            btn.setSelected(i == 0);
            row2.add(btn);
            btn.addActionListener(fastForwardListener);
            group.add(btn);
        }

        row2.add(Box.createHorizontalGlue());
        JButton endLevel = new JButton("Level Select");
        endLevel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.changeView("LevelSelectView");
            }
        });

        row2.add(endLevel);

        add(row1);
        add(row2);
    }
    public void setFps(int fps) {
        fpsLbl.setText("FPS: " + fps);
    }
    public void setVelocity(float velocity) {
        velocityLbl.setText("Velocity: " + String.format("%.1f", velocity));
        if(obj instanceof LandObjective) {
            if(velocity > 75) {
                velocityLbl.setForeground(Color.RED);
                velocityLbl.setText(velocityLbl.getText() + " (Unsafe for landing!)");
            } else {
                velocityLbl.setForeground(new Color(0,128,0));
            }
        }
    }
    public void setAltitude(float altitude) {
        altitudeLbl.setText("Altitude: " + String.format("%.1f", altitude));
    }
    public void setAltitudeText(String text) {
        altitudeLbl.setText(text);
    }

    public void setFastForwardListener(ActionListener listener) {
        this.fastForwardListener = listener;
    }
}