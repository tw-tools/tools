package org.woehlke.tools.config.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.MessageChannels;
import org.woehlke.tools.config.properties.ApplicationProperties;

import static org.woehlke.tools.config.properties.QueueNames.*;

@Configuration
@EnableIntegration
@Import(DataSourceConfig.class)
public class QueueConfig {

    @Bean(LOGBUCH_QUEUE)
    public QueueChannel logbuchChannel() {
        return MessageChannels.queue(
            LOGBUCH_QUEUE,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(LOGBUCH_QUEUE+REPLY)
    public QueueChannel logbuchReplyChannel() {
        return MessageChannels.queue(
            LOGBUCH_QUEUE+REPLY,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(JOB_IMAGES_RESIZE_QUEUE)
    public QueueChannel imagesResizeChannel() {
        return MessageChannels.queue(
            JOB_IMAGES_RESIZE_QUEUE,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(JOB_IMAGES_RESIZE_QUEUE+REPLY)
    public QueueChannel resizeImagesReplyChannel() {
        return MessageChannels.queue(
            JOB_IMAGES_RESIZE_QUEUE+REPLY,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(JOB_IMAGES_INFO_QUEUE)
    public QueueChannel infoImagesChannel() {
        return MessageChannels.queue(
            JOB_IMAGES_INFO_QUEUE,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(JOB_IMAGES_INFO_QUEUE+REPLY)
    public QueueChannel infoImagesJpgReplyChannel() {
        return MessageChannels.queue(
            JOB_IMAGES_INFO_QUEUE+REPLY,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(JOB_RENAME_QUEUE)
    public QueueChannel renameFilesChannel() {
        return MessageChannels.queue(
            JOB_RENAME_QUEUE,
            properties.getQueueCapacity()
        ).get();
    }

    @Bean(JOB_RENAME_QUEUE+REPLY)
    public QueueChannel renameFilesReplyChannel() {
        return MessageChannels.queue(
            JOB_RENAME_QUEUE+REPLY,
            properties.getQueueCapacity()
        ).get();
    }

    @Autowired
    private ApplicationProperties properties;
}
