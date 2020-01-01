package org.woehlke.tools.jobs.images.resize.impl;

import org.springframework.stereotype.Component;
import org.woehlke.tools.jobs.images.info.JobImagesInfoJpg;
import org.woehlke.tools.model.db.entities.Job;

@Component
public class JobImagesInfoJpgImpl extends Thread implements JobImagesInfoJpg {

    private Job job;

    @Override
    public void setRootDirectory(Job job) {
        this.job=job;
    }

    @Override
    public String getJobName() {
        return this.job.getJobCase().getHumanReadable();
    }

    @Override
    public void run() {

    }
}
