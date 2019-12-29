package org.woehlke.tools.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.*;
import org.springframework.integration.jdbc.metadata.JdbcMetadataStore;
import org.springframework.integration.jdbc.store.JdbcChannelMessageStore;
import org.springframework.integration.jdbc.store.channel.ChannelMessageStoreQueryProvider;
import org.springframework.integration.jdbc.store.channel.MySqlChannelMessageStoreQueryProvider;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.PollableChannel;
import org.woehlke.tools.jobs.images.mq.JobScaleImagesPanelGateway;
import org.woehlke.tools.jobs.images.mq.JobScaleImagesQueueGateway;
import org.woehlke.tools.jobs.rename.mq.gateway.JobRenameFilesPanelGateway;
import org.woehlke.tools.jobs.rename.mq.gateway.JobRenameFilesQueueGateway;


import javax.sql.DataSource;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.woehlke.tools.config.QueueNames.*;


@Configuration
@EnableIntegration
public class ToolsApplicationConfig {

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
        return Pollers
            .fixedRate(1, SECONDS)
            .errorChannel("errorChannel");
    }

    @Bean(RENAME_FILES_QUEUE)
    public PollableChannel renameChannel() {
        return MessageChannels.queue(RENAME_FILES_QUEUE,capacity).get();
    }

    @Bean(RENAME_FILES_QUEUE_REPLY)
    public PollableChannel renameRequestChannel() {
        return MessageChannels.queue(RENAME_FILES_QUEUE_REPLY, capacity).get();
    }

    @Bean
    public IntegrationFlow renamePipeline(
        @Qualifier("jobRenameFilesQueueImpl") JobRenameFilesQueueGateway queueGateway,
        @Qualifier("jobRenameFilesPanel") JobRenameFilesPanelGateway panelGateway
    ) {
        return IntegrationFlows
            .from(renameChannel())
            .handle(queueGateway,"listen")        //.log()
            .handle(panelGateway,"listen")         //.log()
            .nullChannel();
    }

    @Bean(SCALE_IMAGES_QUEUE)
    public PollableChannel imagesChannel() {
        return MessageChannels.queue(SCALE_IMAGES_QUEUE,capacity).get();
    }

    @Bean(SCALE_IMAGES_QUEUE_REPLAY)
    public PollableChannel imagesRequestChannel() {
        return MessageChannels.queue(SCALE_IMAGES_QUEUE_REPLAY, capacity).get();
    }

    @Bean
    public IntegrationFlow imagesPipeline(
        @Qualifier("jobScaleImagesQueueImpl") JobScaleImagesQueueGateway queueGateway,
        @Qualifier("jobScaleImagesPanel") JobScaleImagesPanelGateway panelGateway
    ) {
        return IntegrationFlows
            .from(imagesChannel())
            .handle(queueGateway,"listen")        //.log()
            .handle(panelGateway,"listen")         //.log()
            .nullChannel();
    }
}
