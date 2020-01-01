package org.woehlke.tools.view.tabbedpane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.jobs.rename.JobGroupRename;
import org.woehlke.tools.view.mq.JobRenamePanelGateway;
import org.woehlke.tools.view.common.AbstractJobPanel;
import org.woehlke.tools.view.widgets.MyDirectoryChooser;

import java.awt.event.ActionEvent;
import java.io.File;

import static org.woehlke.tools.config.properties.PipelineNames.JOB_RENAME_PANEL;

@Component(JOB_RENAME_PANEL)
public class JobRenamePanel extends AbstractJobPanel implements JobRenamePanelGateway {

    private final JobGroupRename jobGroupRename;

    @Autowired
    public JobRenamePanel(
        JobGroupRename jobGroupRename,
        ApplicationProperties cfg,
        MmiProperties prop,
        MyDirectoryChooser chooser
    ) {
        super(jobGroupRename.getJobName(), cfg, prop, chooser);
        this.jobGroupRename = jobGroupRename;
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
        this.jobGroupRename.setRootDirectory(rootDirectory);
        this.jobGroupRename.start();
    }

    @Override
    public String listen(String payload) {
        updatePanel(payload);
        return payload;
    }

    private class JOB_RENAME_PANEL {
    }
}
