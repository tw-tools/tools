package org.woehlke.tools.view.tabbedpane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.view.mq.JobImagesInfoPanelGateway;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.jobs.images.info.JobImagesInfoGroup;
import org.woehlke.tools.view.common.AbstractJobPanel;
import org.woehlke.tools.view.widgets.MyDirectoryChooser;

import java.awt.event.ActionEvent;
import java.io.File;

@Component("jobImagesInfoPanel")
public class JobImagesInfoPanel extends AbstractJobPanel implements JobImagesInfoPanelGateway {

    private final JobImagesInfoGroup jobImagesInfoGroup;

    @Autowired
    public JobImagesInfoPanel(
        JobImagesInfoGroup jobImagesInfoGroup,
        ApplicationProperties cfg,
        MmiProperties prop,
        MyDirectoryChooser chooser
    ) {
        super(jobImagesInfoGroup.getJobName(), cfg, prop, chooser);
        this.jobImagesInfoGroup = jobImagesInfoGroup;
    }

    @Override
    public void initGUI() {
        super.initUI();
        buttonChooseRootDirAndStartJob.addActionListener(this);
    }

    @Override
    public void start(File rootDirectory) {
        super.started(rootDirectory);
        jobImagesInfoGroup.setRootDirectory(rootDirectory);
        jobImagesInfoGroup.start();
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
