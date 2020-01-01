package org.woehlke.tools.jobs.images.resize.impl;

import org.springframework.stereotype.Component;
import org.woehlke.tools.jobs.images.info.JobImagesInfoPng;

import java.io.File;

@Component
public class JobImagesInfoPngImpl extends Thread implements JobImagesInfoPng {

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
