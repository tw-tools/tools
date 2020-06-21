package org.woehlke.tools.view.tabbedpane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ToolsApplicationProperties;
import org.woehlke.tools.config.properties.ToolsMmiProperties;
import org.woehlke.tools.view.common.AbstractJobPanel;
import org.woehlke.tools.view.mq.LogbuchPanelGateway;

import java.awt.event.ActionEvent;
import java.io.File;

import static org.woehlke.tools.config.properties.ToolsPipelineNames.LOGBUCH_PANEL;


@Component(LOGBUCH_PANEL)
public class LogbuchPanel extends AbstractJobPanel implements LogbuchPanelGateway {

    @Autowired
    public LogbuchPanel(
        ToolsApplicationProperties cfg,
        ToolsMmiProperties prop) {
        super("Logbuch", cfg, prop, null);
        initGUI();
    }

    @Override
    public void initGUI() {
        super.initUI();
    }

    @Override
    public void start(File rootDirectory) {
        super.started(rootDirectory);
    }

    @Override
    public String listen(String payload) {
        updatePanel(payload);
        return payload;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.myActionPerformed(e);
    }
}
