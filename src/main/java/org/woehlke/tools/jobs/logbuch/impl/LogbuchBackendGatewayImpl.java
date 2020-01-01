package org.woehlke.tools.jobs.logbuch.impl;

import org.springframework.stereotype.Component;
import org.woehlke.tools.model.config.JobCase;
import org.woehlke.tools.jobs.logbuch.LogbuchBackendGateway;

import static org.woehlke.tools.config.properties.PipelineNames.LOGBUCH_BACKEND_GATEWAY_IMPL;

@Component(LOGBUCH_BACKEND_GATEWAY_IMPL)
public class LogbuchBackendGatewayImpl implements LogbuchBackendGateway {


    @Override
    public String listen(String payload) {
        return payload;
    }

    @Override
    public void sendMessage(String payload, String category, JobCase job) {

    }
}
