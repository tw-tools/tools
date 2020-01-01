package org.woehlke.tools.model.mq.impl;

import org.springframework.stereotype.Component;
import org.woehlke.tools.config.db.JobCase;
import org.woehlke.tools.model.mq.JobImagesInfoBackendGateway;

@Component
public class JobImagesInfoBackendGatewayImpl implements JobImagesInfoBackendGateway {


    @Override
    public void sendMessage(String payload, String category, JobCase job) {

    }

    @Override
    public String listen(String payload) {
        return payload;
    }
}
