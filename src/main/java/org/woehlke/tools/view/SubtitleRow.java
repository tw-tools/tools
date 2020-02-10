package org.woehlke.tools.view;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.properties.MmiProperties;
import org.woehlke.tools.view.widgets.PanelTextRow;

@Log
@Getter
@Component
public class SubtitleRow extends PanelTextRow {

    private final MmiProperties prop;

    @Autowired
    public SubtitleRow(MmiProperties prop) {
        super(prop.getTitle() + " - " + prop.getSubtitle());
        this.prop = prop;
    }
}
