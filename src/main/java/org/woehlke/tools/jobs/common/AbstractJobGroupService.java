package org.woehlke.tools.jobs.common;

import java.io.File;

public interface AbstractJobGroupService extends Runnable {

    void setRootDirectory(File rootDirectory);

    void start();

    String getJobName();
}
