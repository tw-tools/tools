package org.woehlke.tools.view;

import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class RenameFileAndDirsDialog extends JDialog {

    public RenameFileAndDirsDialog() {
        setTitle("Rename Files and Dirs");
        pack();
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
}
