package org.woehlke.tools.view;

import javax.swing.JFileChooser;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.MmiProperties;

@Log
@Getter
@Component
public class JobRootDirectoryChooser extends JFileChooser {

    private final MmiProperties mmiProperties;

    @Autowired
    public JobRootDirectoryChooser(MmiProperties mmiProperties) {
        this.mmiProperties = mmiProperties;
        if(getCurrentDirectory() == null) {
            setCurrentDirectory(new java.io.File("~"));
        }
        setDialogTitle(mmiProperties.getMyDirectoryChooser());
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
