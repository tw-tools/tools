package org.woehlke.tools.jobs.images.info.impl;

import org.springframework.stereotype.Service;
import org.woehlke.tools.jobs.images.info.JobImagesInfoPng;
import org.woehlke.tools.model.entities.Job;

@Service
public class JobImagesInfoPngImpl extends Thread implements JobImagesInfoPng {

    private Job job;

    @Override
    public void setRootDirectory(Job job) {
        this.job=job;
    }

    @Override
    public String getJobName() {
        return this.job.getJobName();
    }

    @Override
    public void run() {

    }
}
