package org.woehlke.tools.config.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.woehlke.tools.jobs.images.info.JobImagesInfoBackendGateway;
import org.woehlke.tools.jobs.images.resize.JobImagesResizeBackendGateway;
import org.woehlke.tools.jobs.logbuch.LogbuchBackendGateway;
import org.woehlke.tools.jobs.rename.JobRenameBackendGateway;
import org.woehlke.tools.view.mq.JobImagesInfoPanelGateway;
import org.woehlke.tools.view.mq.JobImagesResizePanelGateway;
import org.woehlke.tools.view.mq.JobRenamePanelGateway;
import org.woehlke.tools.view.mq.LogbuchPanelGateway;

import static org.woehlke.tools.config.properties.PipelineNames.*;
import static org.woehlke.tools.config.properties.QueueNames.*;


@Configuration
@EnableIntegration
@Import(QueueConfig.class)
public class PipelineConfig {

    @Bean
    public IntegrationFlow infoImagesPipeline() {
        return IntegrationFlows
            .from(infoImagesChannel)
            .handle(jobImagesInfoPanelGateway,"listen")  //.log()
            .handle(jobImagesInfoBackendGateway,"listen")  //.log()
            .channel(logbuchChannel).get();
    }

    @Bean
    public IntegrationFlow imagesResizePipeline() {
        return IntegrationFlows
            .from(imagesResizeChannel)
            .handle(jobImagesResizePanelGateway,"listen")  //.log()
            .handle(jobImagesResizeBackendGateway,"listen")  //.log()
            .channel(logbuchChannel).get();
    }

    @Bean
    public IntegrationFlow renameFilesPipeline() {
        return IntegrationFlows
            .from(renameFilesChannel)
            .handle(jobRenamePanelGateway,"listen")  //.log()
            .handle(jobRenameBackendGateway,"listen")  //.log()
            .channel(logbuchChannel).get();
    }

    @Bean
    public IntegrationFlow logbuchPipeline() {
        return IntegrationFlows
            .from(logbuchChannel)
            .handle(logbuchBackendGateway,"listen")  //.log()
            .handle(logbuchPanelGateway,"listen")  //.log()
            .nullChannel();
    }

    @Autowired
    @Qualifier(JOB_IMAGES_RESIZE_QUEUE)
    private QueueChannel imagesResizeChannel;

    @Autowired
    @Qualifier(LOGBUCH_QUEUE)
    private QueueChannel logbuchChannel;

    @Autowired
    @Qualifier(JOB_IMAGES_INFO_QUEUE)
    private QueueChannel infoImagesChannel;

    @Autowired
    @Qualifier(JOB_RENAME_QUEUE)
    public QueueChannel renameFilesChannel;

    @Autowired
    @Qualifier(JOB_RENAME_BACKEND_GATEWAY_IMPL)
    private JobRenameBackendGateway jobRenameBackendGateway;

    @Autowired
    @Qualifier(JOB_RENAME_PANEL)
    private JobRenamePanelGateway jobRenamePanelGateway;

    @Autowired
    @Qualifier(LOGBUCH_BACKEND_GATEWAY_IMPL)
    private LogbuchBackendGateway logbuchBackendGateway;

    @Autowired
    @Qualifier(LOGBUCH_PANEL)
    private LogbuchPanelGateway logbuchPanelGateway;

    @Autowired
    @Qualifier(JOB_IMAGES_RESIZE_BACKEND_GATEWAY_IMPL)
    private JobImagesResizeBackendGateway jobImagesResizeBackendGateway;

    @Autowired
    @Qualifier(JOB_IMAGES_RESIZE_PANEL)
    private JobImagesResizePanelGateway jobImagesResizePanelGateway;

    @Autowired
    @Qualifier(JOB_IMAGES_INFO_BACKEND_GATEWAY_IMPL)
    private JobImagesInfoBackendGateway jobImagesInfoBackendGateway;

    @Autowired
    @Qualifier(JOB_IMAGES_INFO_PANEL)
    private JobImagesInfoPanelGateway jobImagesInfoPanelGateway;
}
