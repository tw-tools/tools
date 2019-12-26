package org.woehlke.tools.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.jdbc.metadata.JdbcMetadataStore;
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore;
import org.springframework.integration.jdbc.store.channel.ChannelMessageStoreQueryProvider;
import org.springframework.integration.jdbc.store.channel.MySqlChannelMessageStoreQueryProvider;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.PollableChannel;


import javax.sql.DataSource;


@Configuration
@EnableIntegration
@EnableBatchProcessing
public class SpringIntegrationConfig {

    public static final String LOGBUCH_QUEUE = "org.woehlke.tools.logbuch.request.queue";
    public static final String LOGBUCH_REPLY_QUEUE = "org.woehlke.tools.logbuch.reply.queue";

    @Autowired
    public ChannelMessageStoreQueryProvider queryProvider(){
        return new MySqlChannelMessageStoreQueryProvider();
    }

    @Bean
    public MetadataStore metadataStore(@Autowired DataSource dataSource) {
        return new JdbcMetadataStore(dataSource);
    }

    @Bean
    public JdbcChannelMessageStore messageStore(@Autowired DataSource dataSource){
        JdbcChannelMessageStore messageStore = new JdbcChannelMessageStore(dataSource);
        messageStore.setChannelMessageStoreQueryProvider(queryProvider());
        messageStore.setUsingIdCache(true);
        return messageStore;
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(500)
            .errorChannel("myErrors");
    }

    @Bean(LOGBUCH_QUEUE)
    public PollableChannel logbuchChannel() {
        QueueChannel q = new QueueChannel();
        return q;
    }

    @Bean(LOGBUCH_REPLY_QUEUE)
    public PollableChannel logbuchRequestChannel() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow logbuchPipeline(
        @Qualifier("logbuchQueueService") LogbuchQueueServiceGateway logbuchQueueServiceGateway,
        @Qualifier("panelRenameFiles") PanelRenameFilesGateway panelRenameFiles
    ) {
        Pollers.fixedDelay(5000).maxMessagesPerPoll(10);

        return IntegrationFlows
            .from(logbuchChannel())
            .handle(logbuchQueueServiceGateway,"persist")        //.log()
            .handle(panelRenameFiles,"updatePanel")         //.log()
            .nullChannel();
    }
}
