package org.woehlke.tools.config.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.config.EnableIntegration;
import org.woehlke.tools.jobs.images.info.ImagesInfoJobBackendGateway;
import org.woehlke.tools.jobs.images.resize.ImagesResizeJobBackendGateway;
import org.woehlke.tools.jobs.logbuch.LogbuchJobBackendGateway;
import org.woehlke.tools.jobs.rename.RenameJobBackendGateway;
import org.woehlke.tools.view.mq.JobImagesInfoPanelGateway;
import org.woehlke.tools.view.mq.JobImagesResizePanelGateway;
import org.woehlke.tools.view.mq.JobRenamePanelGateway;
import org.woehlke.tools.view.mq.LogbuchPanelGateway;

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
