package org.woehlke.tools.view.jobs;

import java.io.File;

public interface JobRename extends Runnable {

    void setRootDirectory(File rootDirectory);

    void start();
}
