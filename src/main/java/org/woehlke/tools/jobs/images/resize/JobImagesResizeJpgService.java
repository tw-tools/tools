package org.woehlke.tools.jobs.images.resize;

import org.woehlke.tools.model.entities.Job;

public interface JobImagesResizeJpgService extends Runnable {

    void setRootDirectory(Job job);

    void start();

    String getJobName();
}
