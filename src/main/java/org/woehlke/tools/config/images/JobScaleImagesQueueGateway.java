package org.woehlke.tools.config.images;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.QuereNames.*;

@MessagingGateway(
    name = "myJobScaleImagesQueueGateway",
    defaultRequestChannel = SCALE_IMAGES_QUEUE,
    defaultReplyChannel = SCALE_IMAGES_QUEUE_REPLAY
)
public interface JobScaleImagesQueueGateway {

    @Gateway(
        replyChannel = SCALE_IMAGES_QUEUE_REPLAY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String logbuch);
}
