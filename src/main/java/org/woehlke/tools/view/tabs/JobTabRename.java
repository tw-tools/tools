package org.woehlke.tools.view.tabs;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.jobs.rename.RenameJobGroupServiceService;
import org.woehlke.tools.view.mq.JobEndpointRename;
import org.woehlke.tools.view.tabs.common.AbstractJobTab;
import org.woehlke.tools.view.JobRootDirectoryChooser;

import java.awt.event.ActionEvent;
import java.io.File;

import static org.woehlke.tools.config.properties.PipelineNames.JOB_RENAME_PANEL;

@Log
@Getter
@Component(JOB_RENAME_PANEL)
public class JobTabRename extends AbstractJobTab implements JobEndpointRename {

    private final RenameJobGroupServiceService renameJobGroupService;

    @Autowired
    public JobTabRename(
            RenameJobGroupServiceService renameJobGroupService,
            ApplicationProperties cfg,
            MmiProperties prop,
            JobRootDirectoryChooser chooser
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
