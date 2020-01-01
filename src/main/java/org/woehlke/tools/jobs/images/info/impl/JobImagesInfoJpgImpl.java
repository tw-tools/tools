package org.woehlke.tools.jobs.images.info.impl;

import org.springframework.stereotype.Component;
import org.woehlke.tools.jobs.images.info.JobImagesInfoJpg;
import org.woehlke.tools.model.config.JobCase;
import org.woehlke.tools.model.entities.Job;

@Component
public class JobImagesInfoJpgImpl extends Thread implements JobImagesInfoJpg {

    private Job job;

    @Override
    public void setRootDirectory(Job job) {
        this.job=job;
    }

    @Override
    public String getJobName() {
        return JobCase.JOB_IMAGES_INFO_JPG.getHumanReadable();
    }

    @Override
    public void run() {

    }
}
