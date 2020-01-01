package org.woehlke.tools.jobs.gateways.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.woehlke.tools.jobs.gateways.ImagesInfoJobBackendGateway;
import static org.woehlke.tools.config.properties.PipelineNames.JOB_IMAGES_INFO_BACKEND_GATEWAY_IMPL;

@Service(JOB_IMAGES_INFO_BACKEND_GATEWAY_IMPL)
public class ImagesInfoJobBackendGatewayImpl implements ImagesInfoJobBackendGateway {

    @Override
    public String listen(String payload) {
        logger.info(payload);
        return payload;
    }

    private Log logger = LogFactory.getLog(ImagesInfoJobBackendGatewayImpl.class);
}
