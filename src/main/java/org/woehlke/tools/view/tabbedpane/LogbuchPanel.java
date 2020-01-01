package org.woehlke.tools.view.tabbedpane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.view.common.AbstractJobPanel;
import org.woehlke.tools.view.mq.LogbuchPanelGateway;

import java.awt.event.ActionEvent;
import java.io.File;

@Component("logbuchPanel")
public class LogbuchPanel extends AbstractJobPanel implements LogbuchPanelGateway {

    @Autowired
    public LogbuchPanel(
        ApplicationProperties cfg,
        MmiProperties prop) {
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
