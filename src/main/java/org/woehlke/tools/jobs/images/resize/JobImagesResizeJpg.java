package org.woehlke.tools.jobs.images.resize;

import org.woehlke.tools.model.db.entities.Job;

public interface JobImagesResizeJpg extends Runnable {

    void setRootDirectory(Job job);

    void start();

    String getJobName();
}
