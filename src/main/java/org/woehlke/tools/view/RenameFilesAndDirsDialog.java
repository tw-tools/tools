package org.woehlke.tools.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.File;
import java.util.logging.Logger;

import static javax.swing.BoxLayout.Y_AXIS;

@Component
public class RenameFilesAndDirsDialog extends JDialog implements LoggingCallback {

    private static final Logger log = Logger.getLogger(RenameFilesAndDirsDialog.class.getName());

    private final RenameFilesAndDirs renameFilesAndDirs;

    @Autowired
    public RenameFilesAndDirsDialog(RenameFilesAndDirs renameFilesAndDirs) {
        this.renameFilesAndDirs = renameFilesAndDirs;
        initUI();
    }

    private JTextArea textArea;

    private void initUI() {
        setTitle("Running: Rename Files and Dirs");
        rootPane.setLayout( new BoxLayout(rootPane, Y_AXIS));
        textArea = new JTextArea(20, 80);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        rootPane.add(textArea);
        pack();
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

    public void start(File rootDirectory,JDialog lastDialog){
        boolean dryRun = true;
        renameFilesAndDirs.setRootDirectory(rootDirectory, dryRun,this);
        toFront();
        setVisible(true);
        lastDialog.dispose();
        renameFilesAndDirs.run();
    }

    @Override
    public void info(String msg) {
        msg += "\n";
        log.info(msg);
        textArea.append(msg);
    }
}
