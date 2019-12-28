package org.woehlke.tools.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.ToolsApplicationProperties;
import org.woehlke.tools.view.common.PanelButtonsRow;
import org.woehlke.tools.view.common.PanelTextRow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

@Component
public class ToolsApplicationFrame extends JFrame {

    @Autowired
    public ToolsApplicationFrame(ToolsApplicationProperties prop,
                                 JobRenameFilesPanel jobRenameFilesPanel,
                                 JobScaleImagesPanel jobScaleImagesPanel
    ) throws HeadlessException {
        super(prop.getTitle());
        this.prop = prop;
        this.jobRenameFilesPanel = jobRenameFilesPanel;
        this.jobScaleImagesPanel = jobScaleImagesPanel;
        initUI();
    }

    private final ToolsApplicationProperties prop;
    private final JobRenameFilesPanel jobRenameFilesPanel;
    private final JobScaleImagesPanel jobScaleImagesPanel;

    private void initUI() {
        BoxLayout layout = new BoxLayout(rootPane, Y_AXIS);
        rootPane.setLayout(layout);
        PanelTextRow subtitleRow = new PanelTextRow(prop.getSubtitle());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(prop.getJobRenameFiles(), jobRenameFilesPanel);
        tabbedPane.add(prop.getJobScaleImages(), jobScaleImagesPanel);
        JButton quitButton = new JButton(prop.getQuitButton());
        quitButton.addActionListener(e -> System.exit(0));
        PanelButtonsRow rootPaneButtonRow = new PanelButtonsRow(quitButton);
        rootPane.add(subtitleRow);
        rootPane.add(tabbedPane);
        rootPane.add(rootPaneButtonRow);
        setSize(prop.getWidth(), prop.getHeight());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    public void showme(){
        toFront();
        setVisible(true);
    }
}
