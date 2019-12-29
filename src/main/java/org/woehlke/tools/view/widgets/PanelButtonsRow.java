package org.woehlke.tools.view.widgets;

import javax.swing.*;
import java.awt.*;

public class PanelButtonsRow extends JPanel {

    public PanelButtonsRow(JButton... buttons) {
        this.setLayout(new FlowLayout());
        for(JButton button:buttons){
            this.add(button);
        }
    }
}
