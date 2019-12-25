package org.woehlke.tools.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.db.service.DbLogger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.io.File;

import static javax.swing.BoxLayout.Y_AXIS;

@Component
public class RenameFilesAndDirsDialog extends JFrame implements LoggingCallback {



    private final RenameFilesAndDirs renameFilesAndDirs;
    private final DbLogger dbLogger;

    @Autowired
    public RenameFilesAndDirsDialog(RenameFilesAndDirs renameFilesAndDirs,  DbLogger dbLogger) {
        this.renameFilesAndDirs = renameFilesAndDirs;
        this.dbLogger = dbLogger;
        initUI();
    }

    private JTextArea textArea;
    private JPanel scrollPanePanel;
    private JScrollPane scrollPane;

    private void initUI() {
        String frameTitle = "Running: Rename Files and Dirs";
        setTitle(frameTitle);
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
        rootPane.setLayout( new BoxLayout(rootPane, Y_AXIS));
        rootPane.add(scrollPanePanel);
        pack();
        setSize(800, 500);
        setLocationRelativeTo(null);
    }

    public void start(File rootDirectory){
        boolean dryRun = true;
        renameFilesAndDirs.setRootDirectory(rootDirectory, dryRun,this);
        toFront();
        setVisible(true);
        renameFilesAndDirs.start();
    }

    public void info(String msg) {
        this.dbLogger.info(msg);
        info();
    }

    @Override
    public void info(String msg, String category, String job) {
        this.dbLogger.info(msg,category,job);
        info();
    }

    @Override
    public void info(String msg, String category) {
        this.dbLogger.info(msg,category);
        info();
    }

    public void info() {  StringBuffer b =  this.dbLogger.getInfo();
        textArea.setRows(b.length());
        textArea.setText(b.toString());
    }
}
