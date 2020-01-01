package org.woehlke.tools.view.tabbedpane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.jobs.rename.RenameJobGroupServiceService;
import org.woehlke.tools.view.mq.JobRenamePanelGateway;
import org.woehlke.tools.view.common.AbstractJobPanel;
import org.woehlke.tools.view.widgets.MyDirectoryChooser;

import java.awt.event.ActionEvent;
import java.io.File;

import static org.woehlke.tools.config.properties.PipelineNames.JOB_RENAME_PANEL;

@Component(JOB_RENAME_PANEL)
public class JobRenamePanel extends AbstractJobPanel implements JobRenamePanelGateway {

    private final RenameJobGroupServiceService renameJobGroupService;

    @Autowired
    public JobRenamePanel(
        RenameJobGroupServiceService renameJobGroupService,
        ApplicationProperties cfg,
        MmiProperties prop,
        MyDirectoryChooser chooser
    ) {
        super(renameJobGroupService.getJobName(), cfg, prop, chooser);
        this.renameJobGroupService = renameJobGroupService;
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
        this.renameJobGroupService.setRootDirectory(rootDirectory);
        this.renameJobGroupService.start();
    }

    @Override
    public String listen(String payload) {
        updatePanel(payload);
        return payload;
    }

}
