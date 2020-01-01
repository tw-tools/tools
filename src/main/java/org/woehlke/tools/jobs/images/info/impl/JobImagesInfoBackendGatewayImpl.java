package org.woehlke.tools.jobs.images.info.impl;

import org.springframework.stereotype.Component;
import org.woehlke.tools.jobs.config.JobCase;
import org.woehlke.tools.jobs.images.info.JobImagesInfoBackendGateway;

@Component("jobImagesInfoBackendGatewayImpl")
public class JobImagesInfoBackendGatewayImpl implements JobImagesInfoBackendGateway {


    @Override
    public void sendMessage(String payload, String category, JobCase job) {

    }

    @Override
    public String listen(String payload) {
        return payload;
    }
}
