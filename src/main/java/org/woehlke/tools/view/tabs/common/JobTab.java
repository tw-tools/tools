package org.woehlke.tools.view.tabs.common;

import java.awt.event.ActionListener;
import java.io.File;

public interface JobTab extends ActionListener {

    void initGUI();

    void start(File rootDirectory);

    String listen(String payload);

}
