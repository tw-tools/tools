package org.woehlke.tools.view.common;

import java.awt.event.ActionListener;
import java.io.File;

public interface JobPanel extends ActionListener {

    void initGUI();

    void start(File rootDirectory);

    String listen(String payload);

}
