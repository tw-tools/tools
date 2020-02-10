package org.woehlke.tools.view.tabs;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.view.tabs.common.AbstractJobTab;
import org.woehlke.tools.view.mq.JobEndpointLogbuch;

import java.awt.event.ActionEvent;
import java.io.File;

import static org.woehlke.tools.config.properties.PipelineNames.LOGBUCH_PANEL;

@Log
@Getter
@Component(LOGBUCH_PANEL)
public class JobTabLogbuch extends AbstractJobTab implements JobEndpointLogbuch {

    @Autowired
    public JobTabLogbuch(
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
