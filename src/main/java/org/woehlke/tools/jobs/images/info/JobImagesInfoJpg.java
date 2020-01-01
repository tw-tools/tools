package org.woehlke.tools.jobs.images.info;

import java.io.File;

public interface JobImagesInfoJpg extends Runnable {

    void setRootDirectory(File rootDirectory);

    void start();

    String getJobName();
}
