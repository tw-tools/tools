package org.woehlke.tools.model.jobs.impl;

import org.springframework.stereotype.Component;
import org.woehlke.tools.model.jobs.JobImagesInfoJpg;

import java.io.File;

@Component
public class JobImagesInfoJpgImpl extends Thread implements JobImagesInfoJpg {

    @Override
    public void setRootDirectory(File rootDirectory) {

    }

    @Override
    public String getJobName() {
        return null;
    }

    @Override
    public void run() {

    }
}
