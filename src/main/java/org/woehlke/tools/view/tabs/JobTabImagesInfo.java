package org.woehlke.tools.view.tabs;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.view.mq.JobEndpointImagesInfo;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.jobs.images.info.JobImagesInfoGroupServiceService;
import org.woehlke.tools.view.tabs.common.AbstractJobTab;
import org.woehlke.tools.view.JobRootDirectoryChooser;

import java.awt.event.ActionEvent;
import java.io.File;

import static org.woehlke.tools.config.properties.PipelineNames.JOB_IMAGES_INFO_PANEL;

@Log
@Getter
@Component(JOB_IMAGES_INFO_PANEL)
public class JobTabImagesInfo extends AbstractJobTab implements JobEndpointImagesInfo {

    private final JobImagesInfoGroupServiceService jobImagesInfoGroupService;

    @Autowired
    public JobTabImagesInfo(
            JobImagesInfoGroupServiceService jobImagesInfoGroupService,
            ApplicationProperties cfg,
            MmiProperties properties,
            JobRootDirectoryChooser chooser
    ) {
        super(jobImagesInfoGroupService.getJobName(), cfg, properties, chooser);
        this.jobImagesInfoGroupService = jobImagesInfoGroupService;
        initGUI();
    }

    @Override
    public void initGUI() {
        super.initUI();
        buttonChooseRootDirAndStartJob.addActionListener(this);
    }

    @Override
    public void start(File rootDirectory) {
        super.started(rootDirectory);
        jobImagesInfoGroupService.setRootDirectory(rootDirectory);
        jobImagesInfoGroupService.start();
    }

    @Override
    public String listen(String payload) {
        super.updatePanel(payload);
        return payload;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.myActionPerformed(e);
    }
}
