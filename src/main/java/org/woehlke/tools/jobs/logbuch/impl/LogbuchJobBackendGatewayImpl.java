package org.woehlke.tools.jobs.logbuch.impl;

import org.springframework.stereotype.Service;
import org.woehlke.tools.model.config.JobCase;
import org.woehlke.tools.jobs.logbuch.LogbuchJobBackendGateway;

import static org.woehlke.tools.config.properties.PipelineNames.LOGBUCH_BACKEND_GATEWAY_IMPL;

@Service(LOGBUCH_BACKEND_GATEWAY_IMPL)
public class LogbuchJobBackendGatewayImpl implements LogbuchJobBackendGateway {


    @Override
    public String listen(String payload) {
        return payload;
    }

    @Override
    public void sendMessage(String payload, String category, JobCase job) {

    }
}
