package org.woehlke.tools.view.tabbedpane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.application.ToolsApplicationProperties;
import org.woehlke.tools.config.application.ToolsGuiProperties;
import org.woehlke.tools.config.mq.images.JobScaleImagesPanelGateway;
import org.woehlke.tools.view.jobs.JobScaleImages;
import org.woehlke.tools.view.widgets.MyDirectoryChooser;

import java.awt.event.ActionEvent;
import java.io.File;

@Component
public class JobScaleImagesPanel extends AbstractJobPanel implements JobPanel, JobScaleImagesPanelGateway {

    private final JobScaleImages job;

    @Autowired
    public JobScaleImagesPanel(
        JobScaleImages job,
        ToolsApplicationProperties cfg,
        ToolsGuiProperties prop,
        MyDirectoryChooser chooser
    ) {
        super(job.getJobName(), cfg, prop, chooser);
        this.job = job;
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
       job.setRootDirectory(rootDirectory);
        job.start();
    }

    @Override
    public String listen(String payload) {
        updatePanel(payload);
        return payload;
    }
}
