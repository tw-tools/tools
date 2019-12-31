package org.woehlke.tools.view.jobs;

import java.io.File;

public interface JobRenameFiles extends Runnable {

    void setRootDirectory(File rootDirectory);

    void start();

    String getJobName();
}
