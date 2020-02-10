package org.woehlke.tools.config.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.config.EnableIntegration;
import org.woehlke.tools.jobs.gateways.ImagesInfoJobBackendGateway;
import org.woehlke.tools.jobs.gateways.ImagesResizeJobBackendGateway;
import org.woehlke.tools.jobs.gateways.LogbuchJobBackendGateway;
import org.woehlke.tools.jobs.gateways.RenameJobBackendGateway;
import org.woehlke.tools.view.mq.JobEndpointImagesInfo;
import org.woehlke.tools.view.mq.JobEndpointImagesResize;
import org.woehlke.tools.view.mq.JobEndpointRename;
import org.woehlke.tools.view.mq.JobEndpointLogbuch;

import static org.woehlke.tools.config.properties.PipelineNames.*;
import static org.woehlke.tools.config.properties.PipelineNames.JOB_IMAGES_RESIZE_PANEL;

@Configuration
@EnableIntegration
@Import(QueueConfig.class)
public class EndpointsConfig {

    @Autowired
    @Qualifier(JOB_IMAGES_INFO_BACKEND_GATEWAY_IMPL)
    ImagesInfoJobBackendGateway imagesInfoJobBackendGateway;

    @Autowired
    @Qualifier(JOB_IMAGES_INFO_PANEL)
    JobEndpointImagesInfo jobEndpointImagesInfo;

    @Autowired
    @Qualifier(JOB_RENAME_BACKEND_GATEWAY_IMPL)
     RenameJobBackendGateway renameJobBackendGateway;

    @Autowired
    @Qualifier(JOB_RENAME_PANEL)
    JobEndpointRename jobEndpointRename;

    @Autowired
    @Qualifier(LOGBUCH_BACKEND_GATEWAY_IMPL)
     LogbuchJobBackendGateway logbuchJobBackendGateway;

    @Autowired
    @Qualifier(LOGBUCH_PANEL)
    JobEndpointLogbuch jobEndpointLogbuch;

    @Autowired
    @Qualifier(JOB_IMAGES_RESIZE_BACKEND_GATEWAY_IMPL)
     ImagesResizeJobBackendGateway imagesResizeJobBackendGateway;

    @Autowired
    @Qualifier(JOB_IMAGES_RESIZE_PANEL)
    JobEndpointImagesResize jobTabEndpointImagesResize;
}
