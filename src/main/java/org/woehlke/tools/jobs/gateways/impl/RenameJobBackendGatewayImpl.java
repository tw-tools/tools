package org.woehlke.tools.jobs.gateways.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.jobs.gateways.RenameJobBackendGateway;
import static org.woehlke.tools.config.properties.PipelineNames.JOB_RENAME_BACKEND_GATEWAY_IMPL;


@Service(JOB_RENAME_BACKEND_GATEWAY_IMPL)
public class RenameJobBackendGatewayImpl implements RenameJobBackendGateway {

    @Override
    public String listen(String payload) {
        logger.info(payload);
        return payload;
    }

    private Log logger = LogFactory.getLog(LogbuchJobBackendGatewayImpl.class);
}
