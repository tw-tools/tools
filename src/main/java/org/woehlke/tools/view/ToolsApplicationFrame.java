package org.woehlke.tools.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.view.common.PanelButtonsRow;
import org.woehlke.tools.view.common.PanelTextRow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static javax.swing.BoxLayout.Y_AXIS;

@Component
public class ToolsApplicationFrame extends JFrame {

    @Autowired
    public ToolsApplicationFrame(JobRenameFilesPanel jobRenameFilesPanel,
                                 JobScaleImagesPanel jobScaleImagesPanel) throws HeadlessException {
        super(" TOOLS");
        this.jobRenameFilesPanel = jobRenameFilesPanel;
        this.jobScaleImagesPanel = jobScaleImagesPanel;
        initUI();
    }

    private final JobRenameFilesPanel jobRenameFilesPanel;
    private final JobScaleImagesPanel jobScaleImagesPanel;

    private void initUI() {
        BoxLayout layout = new BoxLayout(rootPane, Y_AXIS);
        rootPane.setLayout(layout);
        PanelTextRow subtitleRow = new PanelTextRow("(c) 2019 Thomas Woehlke");
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Rename Files and Dirs", jobRenameFilesPanel);
        tabbedPane.add("Scale Images", jobScaleImagesPanel);
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });
        PanelButtonsRow rootPaneButtonRow = new PanelButtonsRow(quitButton);
        rootPane.add(subtitleRow);
        rootPane.add(tabbedPane);
        rootPane.add(rootPaneButtonRow);
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
