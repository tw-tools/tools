package org.woehlke.tools.model.jobs;

import java.io.File;

public interface JobImagesResizeJpg extends Runnable {

    void setRootDirectory(File rootDirectory);

    void start();

    String getJobName();
}
