package org.woehlke.tools.view.tabbedpane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.mq.JobImagesInfoPanelGateway;
import org.woehlke.tools.config.properties.ToolsApplicationProperties;
import org.woehlke.tools.config.properties.ToolsGuiProperties;
import org.woehlke.tools.model.jobgroups.JobImagesInfoGroup;
import org.woehlke.tools.view.common.AbstractJobPanel;
import org.woehlke.tools.view.common.JobPanel;
import org.woehlke.tools.view.widgets.MyDirectoryChooser;

import java.awt.event.ActionEvent;
import java.io.File;

@Component
public class JobImagesInfoPanel extends AbstractJobPanel implements JobPanel,
    JobImagesInfoPanelGateway {

    private final JobImagesInfoGroup jobImagesInfoGroup;

    @Autowired
    public JobImagesInfoPanel(
        JobImagesInfoGroup jobImagesInfoGroup,
        ToolsApplicationProperties cfg,
        ToolsGuiProperties prop,
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
