package org.woehlke.tools.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.db.service.LogbuchQueueService;
import org.woehlke.tools.filesystem.RenameFilesAndDirs;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.io.File;

import static javax.swing.BoxLayout.Y_AXIS;

@Component
public class RenameFilesAndDirsPanel extends JPanel {

    private final RenameFilesAndDirs renameFilesAndDirs;
    private final LogbuchQueueService logbuchQueueService;

    @Autowired
    public RenameFilesAndDirsPanel(RenameFilesAndDirs renameFilesAndDirs, LogbuchQueueService logbuchQueueService) {
        this.renameFilesAndDirs = renameFilesAndDirs;
        this.logbuchQueueService = logbuchQueueService;
        initUI();
    }

    private JTextArea textArea;
    private JPanel scrollPanePanel;
    private JScrollPane scrollPane;

    private void initUI() {
        String frameTitle = "Running: Rename Files and Dirs";
        this.setName(frameTitle);
        textArea = new JTextArea(2000, 300);
        scrollPanePanel = new JPanel();
        scrollPanePanel.setLayout( new BoxLayout(scrollPanePanel, Y_AXIS));
        scrollPanePanel.setName(frameTitle);
        String title = "Protokoll: : Rename Files and Dirs";
        int thickness = 20;
        Border border =  BorderFactory.createEmptyBorder(thickness,thickness,thickness,thickness);
        TitledBorder myTitledBorder =  BorderFactory.createTitledBorder(border,title);
        scrollPanePanel.setBorder(myTitledBorder);
        textArea.setEditable(true);
        scrollPane = new JScrollPane(textArea);
        scrollPanePanel.add(scrollPane);
        this.setLayout( new BoxLayout(this, Y_AXIS));
        this.add(scrollPanePanel);
        setSize(800, 500);
    }

    public void start(File rootDirectory){
        boolean dryRun = true;
        renameFilesAndDirs.setRootDirectory(rootDirectory, dryRun);
        renameFilesAndDirs.start();
    }

    public void info(String msg) {
        this.logbuchQueueService.info(msg);
        info();
    }

    public void info(String msg, String category, String job) {
        this.logbuchQueueService.info(msg,category,job);
        info();
    }

    public void info(String msg, String category) {
        this.logbuchQueueService.info(msg,category);
        info();
    }

    public void info() {
        StringBuffer b =  this.logbuchQueueService.getInfo();
        textArea.setRows(b.length());
        textArea.setText(b.toString());
    }
}
