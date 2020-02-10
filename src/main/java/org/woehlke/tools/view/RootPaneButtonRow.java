package org.woehlke.tools.view;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;

import javax.swing.*;

@Log
@Getter
@Component
public class RootPaneButtonRow extends JPanel {

    private final ApplicationProperties cfg;
    private final MmiProperties prop;

    private final JButton quitButton;

    @Autowired
    public RootPaneButtonRow(ApplicationProperties cfg, MmiProperties prop) {
        this.cfg = cfg;
        this.prop = prop;
        this.quitButton = new JButton(prop.getQuitButton());
        this.quitButton.addActionListener(e -> System.exit(0));
    }
}
