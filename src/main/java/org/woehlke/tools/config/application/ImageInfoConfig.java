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
import org.woehlke.tools.jobs.images.info.JobImagesInfoBackendGateway;
import org.woehlke.tools.view.mq.JobImagesInfoPanelGateway;

import static org.woehlke.tools.config.properties.QueueNames.*;

@Configuration
@EnableIntegration
@Import(QueueConfig.class)
public class ImageInfoConfig {

    @Autowired
    @Qualifier("jobImagesInfoBackendGatewayImpl")
    JobImagesInfoBackendGateway jobImagesInfoBackendGateway;

    @Autowired
    @Qualifier("jobImagesInfoPanel")
    JobImagesInfoPanelGateway jobImagesInfoPanelGateway;

    @Autowired
    ApplicationProperties properties;

    @Autowired
    @Qualifier(LOGBUCH_QUEUE)
    QueueChannel logbuchChannel;

    @Autowired
    @Qualifier(JOB_IMAGES_INFO_QUEUE)
    QueueChannel infoImagesChannel;

    @Bean
    public IntegrationFlow infoImagesPipeline() {
        return IntegrationFlows
            .from(infoImagesChannel)
            .handle(jobImagesInfoPanelGateway,"listen")  //.log()
            .handle(jobImagesInfoBackendGateway,"listen")  //.log()
            .channel(logbuchChannel).get();
    }


}
