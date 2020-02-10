package org.woehlke.tools.view;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Log
@Getter
@Component
public class ToolsMenuBar extends JMenuBar {

    private final JMenuBar menuBar;
    private final JMenu fileMenu;
    private final JMenu submenu;
    private final JMenuItem menuItem;
    private final JRadioButtonMenuItem rbMenuItem;
    private final JCheckBoxMenuItem cbMenuItem;

    public ToolsMenuBar() {
        this.menuBar = new JMenuBar();
        this.fileMenu = new JMenu("File");
        this.submenu = null;
        this.menuItem = null;
        this.rbMenuItem = null;
        this.cbMenuItem = null;
    }

}
