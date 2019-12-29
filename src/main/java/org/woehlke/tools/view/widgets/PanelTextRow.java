package org.woehlke.tools.view.widgets;

import javax.swing.*;
import java.awt.*;

public class PanelTextRow extends JPanel {

    public PanelTextRow(String label) {
        this.setLayout(new FlowLayout());
        this.add(new JLabel(label));
    }
}
