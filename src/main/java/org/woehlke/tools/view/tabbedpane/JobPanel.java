package org.woehlke.tools.view.tabbedpane;

import java.awt.event.ActionListener;
import java.io.File;

public interface JobPanel extends ActionListener {

    void initGUI();
    void start(File rootDirectory);
    String listen(String payload);

}
