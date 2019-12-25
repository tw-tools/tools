package org.woehlke.tools.view;


;

import javax.swing.*;
import java.awt.*;
import java.io.File;

@org.springframework.stereotype.Component
public class MyDirectoryChooser extends JFileChooser {

    public MyDirectoryChooser() {
        setCurrentDirectory(new java.io.File("."));
        setDialogTitle("Open Root Dir");
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        setAcceptAllFileFilterUsed(false);
    }

    public File openDialog(Component parent) throws HeadlessException {
        if (showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            return getSelectedFile();
        } else {
            return null;
        }
    }
}
