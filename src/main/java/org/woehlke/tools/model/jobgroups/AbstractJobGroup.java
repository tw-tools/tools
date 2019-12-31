package org.woehlke.tools.model.jobgroups;

import java.io.File;

public interface AbstractJobGroup extends Runnable {

    void setRootDirectory(File rootDirectory);

    void start();

    String getJobName();
}
