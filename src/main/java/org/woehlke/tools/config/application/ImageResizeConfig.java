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
import org.woehlke.tools.config.properties.ApplicationProperties;
import org.woehlke.tools.jobs.images.resize.JobImagesResizeBackendGateway;
import org.woehlke.tools.view.mq.JobImagesResizePanelGateway;

import static org.woehlke.tools.config.properties.QueueNames.*;

@Configuration
@EnableIntegration
@Import(QueueConfig.class)
public class ImageResizeConfig {

    @Autowired
    @Qualifier("jobImagesResizeBackendGatewayImpl")
    JobImagesResizeBackendGateway jobImagesResizeBackendGateway;

    @Autowired
    @Qualifier("jobImagesResizePanel")
    JobImagesResizePanelGateway jobImagesResizePanelGateway;
    @Autowired
    ApplicationProperties properties;

    @Autowired
    @Qualifier(LOGBUCH_QUEUE)
    QueueChannel logbuchChannel;


    @Autowired
    @Qualifier(JOB_IMAGES_RESIZE_QUEUE)
    QueueChannel imagesResizeChannel;

    @Bean
    public IntegrationFlow imagesResizePipeline() {
        return IntegrationFlows
            .from(imagesResizeChannel)
            .handle(jobImagesResizePanelGateway,"listen")  //.log()
            .handle(jobImagesResizeBackendGateway,"listen")  //.log()
            .channel(logbuchChannel).get();
    }



}
