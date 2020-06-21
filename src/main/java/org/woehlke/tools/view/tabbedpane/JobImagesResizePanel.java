package org.woehlke.tools.view.tabbedpane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ToolsApplicationProperties;
import org.woehlke.tools.config.properties.ToolsMmiProperties;
import org.woehlke.tools.model.config.JobCase;
import org.woehlke.tools.model.entities.Job;
import org.woehlke.tools.view.mq.JobImagesResizePanelGateway;
import org.woehlke.tools.jobs.images.resize.JobImagesResizeJpgService;
import org.woehlke.tools.view.common.AbstractJobPanel;
import org.woehlke.tools.view.widgets.MyDirectoryChooser;

import java.awt.event.ActionEvent;
import java.io.File;

import static org.woehlke.tools.config.properties.ToolsPipelineNames.JOB_IMAGES_RESIZE_PANEL;

@Component(JOB_IMAGES_RESIZE_PANEL)
public class JobImagesResizePanel extends AbstractJobPanel implements JobImagesResizePanelGateway {

    private final JobImagesResizeJpgService job;
    private final ToolsApplicationProperties properties;

    @Autowired
    public JobImagesResizePanel(
        JobImagesResizeJpgService job,
        ToolsApplicationProperties cfg,
        ToolsMmiProperties prop,
        MyDirectoryChooser chooser,
        ToolsApplicationProperties properties
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
