package org.woehlke.tools.jobs.images.info.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.woehlke.tools.jobs.images.info.JobImagesInfoGroup;
import org.woehlke.tools.jobs.images.info.JobImagesInfoJpg;
import org.woehlke.tools.jobs.images.info.JobImagesInfoPng;
import org.woehlke.tools.jobs.common.impl.AbstractJobGroupImpl;

import java.io.File;

@Component
public class JobImagesInfoGroupImpl extends AbstractJobGroupImpl implements JobImagesInfoGroup {

    private final JobImagesInfoJpg jobImagesInfoJpg;
    private final JobImagesInfoPng jobImagesInfoPng;

    @Autowired
    public JobImagesInfoGroupImpl(JobImagesInfoJpg jobImagesInfoJpg, JobImagesInfoPng jobImagesInfoPng) {
        this.jobImagesInfoJpg = jobImagesInfoJpg;
        this.jobImagesInfoPng = jobImagesInfoPng;
    }

    @Override
    public void setRootDirectory(File rootDirectory) {
        this.jobImagesInfoJpg.setRootDirectory(rootDirectory);
        this.jobImagesInfoPng.setRootDirectory(rootDirectory);
    }

    @Override
    public String getJobName() {
        return this.jobImagesInfoJpg.getJobName() + " and " + this.jobImagesInfoPng.getJobName();
    }
}
