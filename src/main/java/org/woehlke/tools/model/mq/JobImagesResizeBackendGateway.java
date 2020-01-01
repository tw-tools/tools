package org.woehlke.tools.model.mq;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.woehlke.tools.config.db.JobCase;

import static org.woehlke.tools.config.QueueNames.*;

@MessagingGateway(
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

    void sendMessage(String payload, String category, JobCase job);
}
