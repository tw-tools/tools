package org.woehlke.tools.jobs.gateways.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.woehlke.tools.jobs.gateways.LogbuchJobBackendGateway;

import static org.woehlke.tools.config.properties.ToolsPipelineNames.LOGBUCH_BACKEND_GATEWAY_IMPL;

@Service(LOGBUCH_BACKEND_GATEWAY_IMPL)
public class LogbuchJobBackendGatewayImpl implements LogbuchJobBackendGateway {

    @Override
    public String listen(String payload) {
        logger.info(payload);
        return payload;
    }

    private Log logger = LogFactory.getLog(LogbuchJobBackendGatewayImpl.class);

}
