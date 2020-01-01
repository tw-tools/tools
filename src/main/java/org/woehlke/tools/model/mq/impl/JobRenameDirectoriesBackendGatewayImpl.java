package org.woehlke.tools.model.mq.impl;

import org.springframework.stereotype.Component;
import org.woehlke.tools.config.db.JobCase;
import org.woehlke.tools.model.mq.JobRenameDirectoriesBackendGateway;

@Component
public class JobRenameDirectoriesBackendGatewayImpl implements
    JobRenameDirectoriesBackendGateway {

    @Override
    public void sendMessage(String payload, String category, JobCase job) {

    }

    @Override
    public String listen(String payload) {
        return payload;
    }
}
