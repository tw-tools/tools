package org.woehlke.tools.view.widgets;

import javax.swing.JFileChooser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ToolsMmiProperties;

@Component
public class MyDirectoryChooser extends JFileChooser {

    private final ToolsMmiProperties toolsMmiProperties;

    @Autowired
    public MyDirectoryChooser(ToolsMmiProperties toolsMmiProperties) {
        this.toolsMmiProperties = toolsMmiProperties;
        if(getCurrentDirectory() == null) {
            setCurrentDirectory(new java.io.File("~"));
        }
        setDialogTitle(toolsMmiProperties.getMyDirectoryChooser());
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        setAcceptAllFileFilterUsed(false);
    }

    public java.io.File openDialog(java.awt.Component parent) throws java.awt.HeadlessException {
        if (showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            return getSelectedFile();
        } else {
            return null;
        }
    }
}
