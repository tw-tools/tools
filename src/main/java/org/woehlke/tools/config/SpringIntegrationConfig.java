package org.woehlke.tools.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.*;
import org.springframework.integration.jdbc.metadata.JdbcMetadataStore;
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore;
import org.springframework.integration.jdbc.store.channel.ChannelMessageStoreQueryProvider;
import org.springframework.integration.jdbc.store.channel.MySqlChannelMessageStoreQueryProvider;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.util.ErrorHandler;
import org.woehlke.tools.jobs.mq.impl.ErrorHandlerLog;


import javax.sql.DataSource;

import static java.util.concurrent.TimeUnit.SECONDS;


@Configuration
@EnableIntegration
@EnableBatchProcessing
public class SpringIntegrationConfig {

    public static final String LOGBUCH_QUEUE = "logbuchQueue";
    public static final String LOGBUCH_REPLY_QUEUE = "logbuchReplyQueue";
    public final static String ERROR_CHANNEL = "errorChannel";
    private final String groupId = "groupId";
    private final Integer capacity = 100;

    @Autowired
    public ChannelMessageStoreQueryProvider queryProvider(){
        return new MySqlChannelMessageStoreQueryProvider();
    }

    @Bean
    public MetadataStore metadataStore(@Autowired DataSource dataSource) {
        return new JdbcMetadataStore(dataSource);
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcChannelMessageStore messageStore(){
        JdbcChannelMessageStore messageStore = new JdbcChannelMessageStore(dataSource);
        messageStore.setChannelMessageStoreQueryProvider(queryProvider());
        messageStore.setUsingIdCache(true);
        return messageStore;
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(5, SECONDS).errorChannel("errorChannel");
    }

    @Bean(LOGBUCH_QUEUE)
    public PollableChannel logbuchChannel() {
        //return MessageChannels.queue(messageStore(),groupId).get();
        return MessageChannels.queue(LOGBUCH_QUEUE,capacity).get();
    }

    @Bean(LOGBUCH_REPLY_QUEUE)
    public PollableChannel logbuchRequestChannel() {
        //return MessageChannels.queue(messageStore(),groupId).get();
        return MessageChannels.queue(LOGBUCH_REPLY_QUEUE, capacity).get();
    }

    @Bean
    public IntegrationFlow logbuchPipeline(
        @Qualifier("logbuchQueueService") LogbuchQueueServiceGateway logbuchQueueServiceGateway,
        @Qualifier("panelRenameFiles") PanelRenameFilesGateway panelRenameFiles
    ) {
        return IntegrationFlows
            .from(logbuchChannel())
            .handle(logbuchQueueServiceGateway,"listen")        //.log()
            .handle(panelRenameFiles,"listen")         //.log()
            .nullChannel();
    }

}
