package org.woehlke.tools;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.view.RootPaneButtonRow;
import org.woehlke.tools.view.SubtitleRow;
import org.woehlke.tools.view.ToolsTabbedPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import static javax.swing.BoxLayout.Y_AXIS;

@Log
@Getter
@Component
public class ToolsApplicationFrame extends JFrame implements WindowListener {

    private Dimension screenSize;
    private Rectangle frameBounds;
    private Dimension framePreferredSize;

    private final ApplicationProperties cfg;
    private final MmiProperties prop;
    private final ToolsTabbedPane toolsTabbedPane;

    private final RootPaneButtonRow rootPaneButtonRow;
    private final SubtitleRow subtitleRow;

    @Autowired
    public ToolsApplicationFrame(
        ApplicationProperties cfg,
        MmiProperties prop,
        ToolsTabbedPane toolsTabbedPane,
        RootPaneButtonRow rootPaneButtonRow,
        SubtitleRow subtitleRow
    ) throws HeadlessException {
        super(prop.getTitle());
        this.cfg = cfg;
        this.prop = prop;
        this.toolsTabbedPane = toolsTabbedPane;
        this.rootPaneButtonRow = rootPaneButtonRow;
        this.subtitleRow = subtitleRow;
        initUI();
    }

    private void initUI() {
        BoxLayout layout = new BoxLayout(rootPane, Y_AXIS);
        rootPane.setLayout(layout);
        rootPane.add(this.subtitleRow);
        rootPane.add(this.toolsTabbedPane);
        rootPane.add(this.rootPaneButtonRow);
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
