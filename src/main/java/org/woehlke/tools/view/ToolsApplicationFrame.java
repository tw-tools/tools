package org.woehlke.tools.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static javax.swing.BoxLayout.Y_AXIS;

@Component
public class ToolsApplicationFrame extends JFrame {

    @Autowired
    public ToolsApplicationFrame(RenameFileAndDirsDialog myDialog, RenameFilesAndDirsPanel renameFilesAndDirsPanel) throws HeadlessException {
        super(" TOOLS - (c) 2019 Thomas Woehlke");
        this.myDialog = myDialog;
        this.renameFilesAndDirsPanel = renameFilesAndDirsPanel;
        initUI();
    }

    private JButton quitButton = new JButton("Quit");
    private JButton buttonRenameFilesAndDirs = new JButton("Rename Files and Dirs");
    private final RenameFileAndDirsDialog myDialog;
    private final RenameFilesAndDirsPanel renameFilesAndDirsPanel;

    private void initUI() {
        buttonRenameFilesAndDirs.addActionListener((ActionEvent event) -> {
            myDialog.setModal(true);
            myDialog.toFront();
            myDialog.setVisible(true);
        });
        quitButton.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        BoxLayout layout = new BoxLayout(rootPane, Y_AXIS);
        rootPane.setLayout(layout);
        rootPane.add(buttonRenameFilesAndDirs);
        rootPane.add(renameFilesAndDirsPanel);
        rootPane.add(quitButton);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    public void showme(){
        toFront();
        setVisible(true);
    }
}
