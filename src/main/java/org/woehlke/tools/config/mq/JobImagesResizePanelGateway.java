package org.woehlke.tools.config.mq;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.QueueNames.*;


@MessagingGateway(
    name = "myJobResizeImagesJpgPanelGateway",
    defaultRequestChannel = JOB_RESIZE_IMAGES_QUEUE,
    defaultReplyChannel = JOB_IMAGES_INFO_JPG_QUEUE_REPLAY
)
public interface JobImagesResizePanelGateway {

    @Gateway(
        replyChannel = JOB_IMAGES_INFO_JPG_QUEUE_REPLAY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);
}
