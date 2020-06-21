package org.woehlke.tools.config.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;


@Configuration
@EnableIntegration
@Import({ToolsQueueConfig.class, ToolsEndpointsConfig.class})
public class ToolsPipelineConfig {

    @Autowired
    ToolsQueueConfig toolsQueueConfig;

    @Autowired
    ToolsEndpointsConfig toolsEndpointsConfig;

    @Bean
    public IntegrationFlow infoImagesPipeline() {
        return IntegrationFlows
            .from(toolsQueueConfig.infoImagesChannel())
            .handle(toolsEndpointsConfig.jobImagesInfoPanelGateway,"listen")  //.log()
            .handle(toolsEndpointsConfig.imagesInfoJobBackendGateway,"listen")  //.log()
            .channel(toolsQueueConfig.logbuchChannel()).get();
    }

    @Bean
    public IntegrationFlow imagesResizePipeline() {
        return IntegrationFlows
            .from(toolsQueueConfig.imagesResizeChannel())
            .handle(toolsEndpointsConfig.jobImagesResizePanelGateway,"listen")  //.log()
            .handle(toolsEndpointsConfig.imagesResizeJobBackendGateway,"listen")  //.log()
            .channel(toolsQueueConfig.logbuchChannel()).get();
    }

    @Bean
    public IntegrationFlow renameFilesPipeline() {
        return IntegrationFlows
            .from(toolsQueueConfig.renameFilesChannel())
            .handle(toolsEndpointsConfig.jobRenamePanelGateway,"listen")  //.log()
            .handle(toolsEndpointsConfig.renameJobBackendGateway,"listen")  //.log()
            .channel(toolsQueueConfig.logbuchChannel()).get();
    }

    @Bean
    public IntegrationFlow logbuchPipeline() {
        return IntegrationFlows
            .from(toolsQueueConfig.logbuchChannel())
            .handle(toolsEndpointsConfig.logbuchJobBackendGateway,"listen")  //.log()
            .handle(toolsEndpointsConfig.logbuchPanelGateway,"listen")  //.log()
            .nullChannel();
    }

}
