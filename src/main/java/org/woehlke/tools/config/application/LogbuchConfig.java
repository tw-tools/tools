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
import org.woehlke.tools.jobs.logbuch.LogbuchBackendGateway;
import org.woehlke.tools.view.mq.LogbuchPanelGateway;

import static org.woehlke.tools.config.properties.QueueNames.LOGBUCH_QUEUE;


@Configuration
@EnableIntegration
@Import(QueueConfig.class)
public class LogbuchConfig {

    @Bean
    public IntegrationFlow logbuchPipeline() {
        return IntegrationFlows
            .from(logbuchChannel)
            .handle(logbuchBackendGateway,"listen")  //.log()
            .handle(logbuchPanelGateway,"listen")  //.log()
            .nullChannel();
    }

    @Autowired
    @Qualifier(LOGBUCH_QUEUE)
    QueueChannel logbuchChannel;

    @Autowired
    @Qualifier("lLogbuchBackendGatewayImpl")
    private LogbuchBackendGateway logbuchBackendGateway;

    @Autowired
    @Qualifier("logbuchPanelGatewayImpl")
    private LogbuchPanelGateway logbuchPanelGateway;

}
