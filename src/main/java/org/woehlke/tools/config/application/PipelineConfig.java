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
@Import({QueueConfig.class, EndpointsConfig.class})
public class PipelineConfig {

    @Autowired
    QueueConfig queueConfig;

    @Autowired
    EndpointsConfig endpointsConfig;

    @Bean
    public IntegrationFlow infoImagesPipeline() {
        return IntegrationFlows
            .from(queueConfig.infoImagesChannel())
            .handle(endpointsConfig.jobEndpointImagesInfo,"listen")  //.log()
            .handle(endpointsConfig.imagesInfoJobBackendGateway,"listen")  //.log()
            .channel(queueConfig.logbuchChannel()).get();
    }

    @Bean
    public IntegrationFlow imagesResizePipeline() {
        return IntegrationFlows
            .from(queueConfig.imagesResizeChannel())
            .handle(endpointsConfig.jobTabEndpointImagesResize,"listen")  //.log()
            .handle(endpointsConfig.imagesResizeJobBackendGateway,"listen")  //.log()
            .channel(queueConfig.logbuchChannel()).get();
    }

    @Bean
    public IntegrationFlow renameFilesPipeline() {
        return IntegrationFlows
            .from(queueConfig.renameFilesChannel())
            .handle(endpointsConfig.jobEndpointRename,"listen")  //.log()
            .handle(endpointsConfig.renameJobBackendGateway,"listen")  //.log()
            .channel(queueConfig.logbuchChannel()).get();
    }

    @Bean
    public IntegrationFlow logbuchPipeline() {
        return IntegrationFlows
            .from(queueConfig.logbuchChannel())
            .handle(endpointsConfig.logbuchJobBackendGateway,"listen")  //.log()
            .handle(endpointsConfig.jobEndpointLogbuch,"listen")  //.log()
            .nullChannel();
    }

}
