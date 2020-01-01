package org.woehlke.tools.view.tabbedpane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.model.db.config.JobCase;
import org.woehlke.tools.model.db.entities.Job;
import org.woehlke.tools.view.mq.JobImagesResizePanelGateway;
import org.woehlke.tools.jobs.images.resize.JobImagesResizeJpg;
import org.woehlke.tools.view.common.AbstractJobPanel;
import org.woehlke.tools.view.widgets.MyDirectoryChooser;

import java.awt.event.ActionEvent;
import java.io.File;

import static org.woehlke.tools.config.properties.PipelineNames.JOB_IMAGES_RESIZE_PANEL;

@Component(JOB_IMAGES_RESIZE_PANEL)
public class JobImagesResizePanel extends AbstractJobPanel implements JobImagesResizePanelGateway {

    private final JobImagesResizeJpg job;
    private final ApplicationProperties properties;

    @Autowired
    public JobImagesResizePanel(
        JobImagesResizeJpg job,
        ApplicationProperties cfg,
        MmiProperties prop,
        MyDirectoryChooser chooser,
        ApplicationProperties properties) {
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
