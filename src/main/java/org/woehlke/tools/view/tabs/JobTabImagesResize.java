package org.woehlke.tools.view.tabs;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.model.config.JobCase;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.view.mq.JobEndpointImagesResize;
import org.woehlke.tools.jobs.images.resize.JobImagesResizeJpgService;
import org.woehlke.tools.view.tabs.common.AbstractJobTab;
import org.woehlke.tools.view.JobRootDirectoryChooser;

import java.awt.event.ActionEvent;
import java.io.File;

import static org.woehlke.tools.config.properties.PipelineNames.JOB_IMAGES_RESIZE_PANEL;

@Log
@Getter
@Component(JOB_IMAGES_RESIZE_PANEL)
public class JobTabImagesResize extends AbstractJobTab implements JobEndpointImagesResize {

    private final JobImagesResizeJpgService job;
    private final ApplicationProperties properties;

    @Autowired
    public JobTabImagesResize(
            JobImagesResizeJpgService job,
            ApplicationProperties cfg,
            MmiProperties prop,
            JobRootDirectoryChooser chooser,
            ApplicationProperties properties
    ) {
        super(job.getJobName(), cfg, prop, chooser);
        this.job = job;
        this.properties = properties;
        initGUI();
    }
    public void initGUI() {
        super.initUI();
        buttonChooseRootDirAndStartJob.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.myActionPerformed(e);
    }

    public void start(File rootDirectory){
        super.started(rootDirectory);
        Job myJob = Job.create(
            JobCase.JOB_IMAGES_RESIZE,
            rootDirectory.getAbsoluteFile(),
            properties.getDryRun(),
            properties.getDbActive());
       job.setRootDirectory(myJob);
       job.start();
    }

    @Override
    public String listen(String payload) {
        updatePanel(payload);
        return payload;
    }
}
