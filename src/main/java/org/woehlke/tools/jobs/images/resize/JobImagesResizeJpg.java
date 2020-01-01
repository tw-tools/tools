package org.woehlke.tools.jobs.images.resize;

import java.io.File;

public interface JobImagesResizeJpg extends Runnable {

    void setRootDirectory(File rootDirectory);

    void start();

    String getJobName();
}
