package org.woehlke.tools.jobs.images.info;

import org.woehlke.tools.model.entities.Job;

public interface JobImagesInfoJpgService extends Runnable {

    void setRootDirectory(Job job);

    void start();

    String getJobName();
}
