package org.woehlke.tools.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.woehlke.tools.config.mq.JobImagesInfoPanelGateway;
import org.woehlke.tools.config.mq.LogbuchPanelGateway;
import org.woehlke.tools.model.mq.JobImagesInfoBackendGateway;
import org.woehlke.tools.model.mq.LogbuchBackendGateway;
import org.woehlke.tools.config.properties.ToolsApplicationProperties;
import org.woehlke.tools.config.mq.JobImagesResizePanelGateway;
import org.woehlke.tools.model.mq.JobImagesResizeBackendGateway;
import org.woehlke.tools.config.mq.JobRenameFilesPanelGateway;
import org.woehlke.tools.model.mq.JobRenameFilesBackendGateway;

import javax.sql.DataSource;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.woehlke.tools.config.QueueNames.*;


@Configuration
@EnableIntegration
public class ToolsApplicationConfig {

    @Autowired
    private ToolsApplicationProperties properties;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public ChannelMessageStoreQueryProvider queryProvider(){
        return new MySqlChannelMessageStoreQueryProvider();
    }

    @Bean
    public MetadataStore metadataStore(
        @Autowired DataSource dataSource
    ) {
        return new JdbcMetadataStore(dataSource);
    }

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

    // ---------------------------------------------------------

    @Bean(JOB_RENAME_FILES_QUEUE)
    public PollableChannel renameFilesChannel() {
        return MessageChannels.queue(
            JOB_RENAME_FILES_QUEUE,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(JOB_RENAME_DIRECTORIES_QUEUE)
    public PollableChannel renameDirectoriesChannel() {
        return MessageChannels.queue(
            JOB_RENAME_DIRECTORIES_QUEUE,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(JOB_RESIZE_IMAGES_QUEUE)
    public PollableChannel imagesResizeChannel() {
        return MessageChannels.queue(
            JOB_RESIZE_IMAGES_QUEUE,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(JOB_IMAGES_INFO_JPG_QUEUE)
    public PollableChannel infoImagesJpgChannel() {
        return MessageChannels.queue(
            JOB_IMAGES_INFO_JPG_QUEUE,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(JOB_IMAGES_INFO_PNG_QUEUE)
    public PollableChannel infoImagesPngChannel() {
        return MessageChannels.queue(
            JOB_IMAGES_INFO_PNG_QUEUE,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(LOGBUCH_QUEUE)
    public PollableChannel logbuchChannel() {
        return MessageChannels.queue(
            LOGBUCH_QUEUE,
            properties.getQueueCapacity()
        ).get();
    }

    // ---------------------------------------------------------

    @Bean(JOB_RENAME_FILES_QUEUE_REPLAY)
    public PollableChannel renameFilesReplyChannel() {
        return MessageChannels.queue(
            JOB_RENAME_FILES_QUEUE_REPLAY,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(JOB_RENAME_DIRECTORIES_QUEUE_REPLAY)
    public PollableChannel renameDirectoriesReplyChannel() {
        return MessageChannels.queue(
            JOB_RENAME_DIRECTORIES_QUEUE_REPLAY,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(JOB_RESIZE_IMAGES_QUEUE_REPLAY)
    public PollableChannel resizeImagesReplyChannel() {
        return MessageChannels.queue(
            JOB_RESIZE_IMAGES_QUEUE_REPLAY,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(JOB_IMAGES_INFO_JPG_QUEUE_REPLAY)
    public PollableChannel infoImagesJpgReplyChannel() {
        return MessageChannels.queue(
            JOB_IMAGES_INFO_JPG_QUEUE_REPLAY,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(JOB_IMAGES_INFO_PNG_QUEUE_REPLAY)
    public PollableChannel infoImagesPngReplyChannel() {
        return MessageChannels.queue(
            JOB_IMAGES_INFO_PNG_QUEUE_REPLAY,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(LOGBUCH_QUEUE_REPLAY)
    public PollableChannel logbuchReplyChannel() {
        return MessageChannels.queue(
            LOGBUCH_QUEUE_REPLAY,
            properties.getQueueCapacity()
        ).get();
    }

    // ---------------------------------------------------------

    @Bean
    public IntegrationFlow renameFilesPipeline(
        @Autowired JobRenameFilesBackendGateway backendGateway,
        @Autowired JobRenameFilesPanelGateway panelGateway
    ) {
        return IntegrationFlows
            .from(renameFilesChannel())
            .handle(panelGateway,"listen")  //.log()
            .handle(backendGateway,"listen")  //.log()
            .channel(renameDirectoriesChannel()).get();
    }

    @Bean
    public IntegrationFlow renameDirectoriesPipeline(
        @Autowired JobRenameFilesBackendGateway backendGateway,
        @Autowired JobRenameFilesPanelGateway panelGateway
    ) {
        return IntegrationFlows
            .from(renameDirectoriesChannel())
            .handle(panelGateway,"listen")  //.log()
            .handle(backendGateway,"listen")  //.log()
            .nullChannel();
    }

    @Bean
    public IntegrationFlow imagesResizePipeline(
        @Autowired JobImagesResizeBackendGateway backendGateway,
        @Autowired JobImagesResizePanelGateway panelGateway
    ) {
        return IntegrationFlows
            .from(imagesResizeChannel())
            .handle(panelGateway,"listen")  //.log()
            .handle(backendGateway,"listen")  //.log()
            .nullChannel();
    }

    @Bean
    public IntegrationFlow infoImagesJpgPipeline(
        @Autowired JobImagesInfoBackendGateway backendGateway,
        @Autowired JobImagesInfoPanelGateway panelGateway
    ) {
        return IntegrationFlows
            .from(infoImagesJpgChannel())
            .handle(backendGateway,"listen")  //.log()
            .handle(panelGateway,"listen")  //.log()
            .channel(infoImagesPngChannel()).get();
    }

    @Bean
    public IntegrationFlow infoImagesPngPipeline(
        @Autowired JobImagesInfoBackendGateway backendGateway,
        @Autowired JobImagesInfoPanelGateway panelGateway
    ) {
        return IntegrationFlows
            .from(infoImagesPngChannel())
            .handle(panelGateway,"listen")  //.log()
            .handle(backendGateway,"listen")  //.log()
            .nullChannel();
    }

    @Bean
    public IntegrationFlow logbuchPipeline(
        @Autowired LogbuchBackendGateway backendGateway,
        @Autowired LogbuchPanelGateway panelGateway
    ) {
        return IntegrationFlows
            .from(infoImagesPngChannel())
            .handle(panelGateway,"listen")  //.log()
            .handle(backendGateway,"listen")  //.log()
            .nullChannel();
    }
}
