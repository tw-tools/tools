package org.woehlke.tools.model.jobs;

import java.io.File;

public interface JobImagesInfoJpg extends Runnable {

    void setRootDirectory(File rootDirectory);

    void start();

    String getJobName();
}
