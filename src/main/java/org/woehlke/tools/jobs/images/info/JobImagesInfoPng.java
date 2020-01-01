package org.woehlke.tools.jobs.images.info;

import java.io.File;

public interface JobImagesInfoPng extends Runnable {

    void setRootDirectory(File rootDirectory);

    void start();

    String getJobName();

}
