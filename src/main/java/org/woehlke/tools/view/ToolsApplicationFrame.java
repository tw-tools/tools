package org.woehlke.tools.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.application.ToolsApplicationProperties;
import org.woehlke.tools.config.application.ToolsGuiProperties;
import org.woehlke.tools.view.tabbedpane.JobRenameFilesPanel;
import org.woehlke.tools.view.tabbedpane.JobScaleImagesPanel;
import org.woehlke.tools.view.tabbedpane.JobTablePanel;
import org.woehlke.tools.view.tabbedpane.JobTreePanel;
import org.woehlke.tools.view.widgets.PanelButtonsRow;
import org.woehlke.tools.view.widgets.PanelTextRow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static javax.swing.BoxLayout.Y_AXIS;

@Component
public class ToolsApplicationFrame extends JFrame implements WindowListener {

    @Autowired
    public ToolsApplicationFrame(
        ToolsApplicationProperties cfg,
        ToolsGuiProperties prop,
        JobRenameFilesPanel jobRenameFilesPanel,
        JobScaleImagesPanel jobScaleImagesPanel,
        JobTablePanel jobTablePanel,
        JobTreePanel jobTreePanel
    ) throws HeadlessException {
        super(prop.getTitle());
        this.cfg = cfg;
        this.prop = prop;
        this.jobRenameFilesPanel = jobRenameFilesPanel;
        this.jobScaleImagesPanel = jobScaleImagesPanel;
        this.jobTablePanel = jobTablePanel;
        this.jobTreePanel = jobTreePanel;
        initUI();
    }

    private final ToolsApplicationProperties cfg;
    private final ToolsGuiProperties prop;
    private final JobRenameFilesPanel jobRenameFilesPanel;
    private final JobScaleImagesPanel jobScaleImagesPanel;
    private final JobTablePanel jobTablePanel;
    private final JobTreePanel jobTreePanel;

    private void initUI() {
        BoxLayout layout = new BoxLayout(rootPane, Y_AXIS);
        rootPane.setLayout(layout);
        PanelTextRow subtitleRow = new PanelTextRow(prop.getTitle() + " - " + prop.getSubtitle());
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(prop.getJobRenameFiles(), jobRenameFilesPanel);
        tabbedPane.add(prop.getJobScaleImages(), jobScaleImagesPanel);
        tabbedPane.add(prop.getJobtableTitle(), jobTablePanel);
        tabbedPane.add(prop.getJobtableTitle(), jobTreePanel);
        JButton quitButton = new JButton(prop.getQuitButton());
        quitButton.addActionListener(e -> System.exit(0));
        PanelButtonsRow rootPaneButtonRow = new PanelButtonsRow(quitButton);
        rootPane.add(subtitleRow);
        rootPane.add(tabbedPane);
        rootPane.add(rootPaneButtonRow);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = cfg.getWidth();
        double height = cfg.getHeight();
        double startX = (screenSize.getWidth() - width) / 2d;
        double startY = (screenSize.getHeight() - height) / 2d;
        int myheight = Double.valueOf(height).intValue();
        int mywidth = Double.valueOf(width).intValue();
        int mystartX = Double.valueOf(startX).intValue();
        int mystartY = Double.valueOf(startY).intValue();
        frameBounds = new Rectangle(mystartX, mystartY, mywidth, myheight);
        framePreferredSize = new Dimension(mywidth, myheight);
    }

    private Dimension screenSize;
    private Rectangle frameBounds;
    private Dimension framePreferredSize;

    public void showme(){
        setSize(framePreferredSize);
        setPreferredSize(framePreferredSize);
        setBounds(frameBounds);
        pack();
        toFront();
        setVisible(true);
    }


    @Override
    public void windowOpened(WindowEvent e) {
        showme();
    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        showme();
    }

    @Override
    public void windowActivated(WindowEvent e) {
        showme();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
