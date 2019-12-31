package org.woehlke.tools.view.tabbedpane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ToolsApplicationProperties;
import org.woehlke.tools.config.properties.ToolsGuiProperties;
import org.woehlke.tools.config.mq.JobImagesResizePanelGateway;
import org.woehlke.tools.model.jobs.JobImagesResizeJpg;
import org.woehlke.tools.view.common.AbstractJobPanel;
import org.woehlke.tools.view.common.JobPanel;
import org.woehlke.tools.view.widgets.MyDirectoryChooser;

import java.awt.event.ActionEvent;
import java.io.File;

@Component
public class JobImagesResizePanel extends AbstractJobPanel implements JobPanel,
    JobImagesResizePanelGateway {

    private final JobImagesResizeJpg job;

    @Autowired
    public JobImagesResizePanel(
        JobImagesResizeJpg job,
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
