package org.woehlke.tools.jobs.gateways.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.woehlke.tools.jobs.gateways.ImagesResizeJobBackendGateway;

import static org.woehlke.tools.config.properties.ToolsPipelineNames.JOB_IMAGES_RESIZE_BACKEND_GATEWAY_IMPL;

@Service(JOB_IMAGES_RESIZE_BACKEND_GATEWAY_IMPL)
public class ImagesResizeJobBackendGatewayImpl implements ImagesResizeJobBackendGateway {

    @Override
    public String listen(String payload){
        logger.info(payload);
        return payload;
    }

    private Log logger = LogFactory.getLog(ImagesResizeJobBackendGatewayImpl.class);
}
