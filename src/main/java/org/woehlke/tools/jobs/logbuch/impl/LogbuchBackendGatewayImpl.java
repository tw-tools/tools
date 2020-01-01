package org.woehlke.tools.jobs.logbuch.impl;

import org.springframework.stereotype.Component;
import org.woehlke.tools.model.db.config.JobCase;
import org.woehlke.tools.jobs.logbuch.LogbuchBackendGateway;

@Component("logbuchBackendGatewayImpl")
public class LogbuchBackendGatewayImpl implements LogbuchBackendGateway {

    @Override
    public String listen(String payload) {
        return payload;
    }

    @Override
    public void sendMessage(String payload, String category, JobCase job) {

    }
}
