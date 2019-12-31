package org.woehlke.tools.config.mq.backend;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.QueueNames.*;

@MessagingGateway(
    name = "myJobImagesResizeBackendGatewayy",
    defaultRequestChannel = JOB_RESIZE_IMAGES_QUEUE,
    defaultReplyChannel = JOB_RESIZE_IMAGES_QUEUE_REPLAY
)
public interface JobImagesResizeBackendGateway {

    @Gateway(
        replyChannel = JOB_RESIZE_IMAGES_QUEUE_REPLAY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String logbuch);
}
