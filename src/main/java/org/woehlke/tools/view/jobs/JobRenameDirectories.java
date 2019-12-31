package org.woehlke.tools.view.jobs;

import java.io.File;

public interface JobRenameDirectories extends Runnable  {

    void setRootDirectory(File rootDirectory);

    void start();

    String getJobName();
}
