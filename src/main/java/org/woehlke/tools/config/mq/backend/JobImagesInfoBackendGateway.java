package org.woehlke.tools.config.mq.backend;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.QueueNames.JOB_IMAGES_INFO_JPG_QUEUE;
import static org.woehlke.tools.config.QueueNames.JOB_IMAGES_INFO_JPG_QUEUE_REPLAY;

@MessagingGateway(
    name = "myJobImagesInfoBackendGateway",
    defaultRequestChannel = JOB_IMAGES_INFO_JPG_QUEUE,
    defaultReplyChannel = JOB_IMAGES_INFO_JPG_QUEUE_REPLAY
)
public interface JobImagesInfoBackendGateway {

    @Gateway(
        replyChannel = JOB_IMAGES_INFO_JPG_QUEUE_REPLAY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);
}
