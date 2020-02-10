package org.woehlke.tools.view;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.view.tabs.*;
import org.woehlke.tools.view.tabs.JobTabTree;

import javax.swing.*;

@Log
@Getter
@Component
public class ToolsTabbedPane extends JTabbedPane {

    private final JobTabRename jobRenameFilesPanel;
    private final JobTabImagesResize jobScaleImagesPanel;
    private final JobTabImagesInfo jobImagesInfoPanel;
    private final JobTabLogbuch logbuchPanel;
    private final JobTablePanel jobTablePanel;
    private final JobTabTree jobTabTree;

    private final ApplicationProperties cfg;
    private final MmiProperties prop;

    @Autowired
    public ToolsTabbedPane(
        final JobTabRename jobRenameFilesPanel,
        final JobTabImagesResize jobScaleImagesPanel,
        final JobTabImagesInfo jobImagesInfoPanel,
        final JobTabLogbuch logbuchPanel,
        final JobTablePanel jobTablePanel,
        final JobTabTree jobTabTree,
        final ApplicationProperties cfg,
        final MmiProperties prop
    ) {
        this.cfg = cfg;
        this.prop = prop;
        this.jobRenameFilesPanel = jobRenameFilesPanel;
        this.jobScaleImagesPanel = jobScaleImagesPanel;
        this.jobImagesInfoPanel = jobImagesInfoPanel;
        this.logbuchPanel = logbuchPanel;
        this.jobTablePanel = jobTablePanel;
        this.jobTabTree = jobTabTree;
        this.add(this.prop.getJobRenameFiles(), this.jobRenameFilesPanel);
        this.add(this.prop.getJobScaleImages(), this.jobScaleImagesPanel);
        this.add(this.prop.getJobImagesInfo(), this.jobImagesInfoPanel);
        this.add("Logbuch",this.logbuchPanel);
        this.add("Job Table Test", this.jobTablePanel);
        this.add("Job Tree Test", this.jobTabTree);
    }
}
