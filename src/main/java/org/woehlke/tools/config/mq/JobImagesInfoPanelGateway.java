package org.woehlke.tools.config.mq;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.QueueNames.*;

@MessagingGateway(
    name = "myJobImagesInfoPanelGateway",
    defaultRequestChannel = JOB_IMAGES_INFO_JPG_QUEUE,
    defaultReplyChannel = JOB_IMAGES_INFO_JPG_QUEUE_REPLAY
)
public interface JobImagesInfoPanelGateway {

    @Gateway(
        replyChannel = JOB_IMAGES_INFO_JPG_QUEUE_REPLAY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);
}
