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
import org.woehlke.tools.view.mq.JobImagesInfoPanelGateway;
import org.woehlke.tools.view.mq.JobImagesResizePanelGateway;
import org.woehlke.tools.view.mq.JobRenamePanelGateway;
import org.woehlke.tools.view.mq.LogbuchPanelGateway;

import static org.woehlke.tools.config.properties.ToolsPipelineNames.*;
import static org.woehlke.tools.config.properties.ToolsPipelineNames.JOB_IMAGES_RESIZE_PANEL;

@Configuration
@EnableIntegration
@Import(ToolsQueueConfig.class)
public class ToolsEndpointsConfig {

    @Autowired
    @Qualifier(JOB_IMAGES_INFO_BACKEND_GATEWAY_IMPL)
    ImagesInfoJobBackendGateway imagesInfoJobBackendGateway;

    @Autowired
    @Qualifier(JOB_IMAGES_INFO_PANEL)
    JobImagesInfoPanelGateway jobImagesInfoPanelGateway;

    @Autowired
    @Qualifier(JOB_RENAME_BACKEND_GATEWAY_IMPL)
     RenameJobBackendGateway renameJobBackendGateway;

    @Autowired
    @Qualifier(JOB_RENAME_PANEL)
     JobRenamePanelGateway jobRenamePanelGateway;

    @Autowired
    @Qualifier(LOGBUCH_BACKEND_GATEWAY_IMPL)
     LogbuchJobBackendGateway logbuchJobBackendGateway;

    @Autowired
    @Qualifier(LOGBUCH_PANEL)
     LogbuchPanelGateway logbuchPanelGateway;

    @Autowired
    @Qualifier(JOB_IMAGES_RESIZE_BACKEND_GATEWAY_IMPL)
     ImagesResizeJobBackendGateway imagesResizeJobBackendGateway;

    @Autowired
    @Qualifier(JOB_IMAGES_RESIZE_PANEL)
     JobImagesResizePanelGateway jobImagesResizePanelGateway;
}
