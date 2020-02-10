package org.woehlke.tools.view;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.view.widgets.PanelButtonsRow;

@Log
@Getter
@Component
public class ToolsApplicationFramePanelButtonsRow extends PanelButtonsRow {

    private final ApplicationProperties cfg;
    private final MmiProperties prop;

    @Autowired
    public ToolsApplicationFramePanelButtonsRow(ApplicationProperties cfg, MmiProperties prop) {
        this.cfg = cfg;
        this.prop = prop;
    }
}
