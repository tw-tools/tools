package org.woehlke.tools.model.mq.impl;

import org.springframework.stereotype.Component;
import org.woehlke.tools.config.db.JobCase;
import org.woehlke.tools.model.mq.LogbuchBackendGateway;

@Component
public class LogbuchBackendGatewayImpl implements LogbuchBackendGateway {

    @Override
    public String listen(String payload) {
        return payload;
    }

    @Override
    public void sendMessage(String payload, String category, JobCase job) {

    }
}
