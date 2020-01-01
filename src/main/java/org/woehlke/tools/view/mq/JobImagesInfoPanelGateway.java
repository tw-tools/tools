package org.woehlke.tools.view.mq;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.properties.QueueNames.*;

@MessagingGateway(
    defaultRequestChannel = JOB_IMAGES_INFO_QUEUE,
    defaultReplyChannel = JOB_IMAGES_INFO_QUEUE_REPLY
)
public interface JobImagesInfoPanelGateway {

    @Gateway(
        replyChannel = JOB_IMAGES_INFO_QUEUE_REPLY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);
}
