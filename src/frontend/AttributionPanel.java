package frontend;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class AttributionPanel extends JPanel {
    public AttributionPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(600, 300));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        String attribText = """
        Planets (1.0)

        Created/distributed by Kenney (www.kenney.nl)
        Creation date: 28-07-2022
        
                ------------------------------
        
        License: (Creative Commons Zero, CC0)
        http://creativecommons.org/publicdomain/zero/1.0/
        
        This content is free to use in personal, educational and commercial projects.
        
        Support us by crediting Kenney or www.kenney.nl (this is not mandatory) 

                ------------------------------
                
        Space Shooter Extension

        by  Kenney Vleugels (Kenney.nl)

                ------------------------------

        License (Creative Commons Zero, CC0)
        http://creativecommons.org/publicdomain/zero/1.0/

        You may use these assets in personal and commercial projects.
        Credit (Kenney or www.kenney.nl) would be nice but is not mandatory.
        """;
        JTextArea text = new JTextArea(attribText);
        text.setEditable(false);

        JScrollPane scroll = new JScrollPane(text);
        //scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scroll);
    }
}
