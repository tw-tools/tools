package org.woehlke.tools.jobs.images.info;

import org.woehlke.tools.model.db.entities.Job;

import java.io.File;

public interface JobImagesInfoJpg extends Runnable {

    void setRootDirectory(Job job);

    void start();

    String getJobName();
}
