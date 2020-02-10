package org.woehlke.tools.view;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.view.tabbedpane.*;

import javax.swing.*;

@Log
@Getter
@Component
public class ToolsTabbedPane extends JTabbedPane {

    private final JobRenamePanel jobRenameFilesPanel;
    private final JobImagesResizePanel jobScaleImagesPanel;
    private final JobImagesInfoPanel jobImagesInfoPanel;
    private final LogbuchPanel logbuchPanel;
    private final JobTablePanel jobTablePanel;
    private final JobTreePanel jobTreePanel;
    private final ApplicationProperties cfg;
    private final MmiProperties prop;

    @Autowired
    public ToolsTabbedPane(JobRenamePanel jobRenameFilesPanel, JobImagesResizePanel jobScaleImagesPanel, JobImagesInfoPanel jobImagesInfoPanel, LogbuchPanel logbuchPanel, JobTablePanel jobTablePanel, JobTreePanel jobTreePanel, ApplicationProperties cfg, MmiProperties prop) {
        this.jobRenameFilesPanel = jobRenameFilesPanel;
        this.jobScaleImagesPanel = jobScaleImagesPanel;
        this.jobImagesInfoPanel = jobImagesInfoPanel;
        this.logbuchPanel = logbuchPanel;
        this.jobTablePanel = jobTablePanel;
        this.jobTreePanel = jobTreePanel;
        this.add(prop.getJobRenameFiles(), jobRenameFilesPanel);
        this.add(prop.getJobScaleImages(), jobScaleImagesPanel);
        this.add(prop.getJobImagesInfo(), jobImagesInfoPanel);
        this.add("Logbuch",logbuchPanel);
        this.add("Job Table Test", jobTablePanel);
        this.add("Job Tree Test", jobTreePanel);
        this.cfg = cfg;
        this.prop = prop;
    }
}
