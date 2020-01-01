package org.woehlke.tools.jobs.images.resize;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.woehlke.tools.model.config.JobCase;

import static org.woehlke.tools.config.properties.QueueNames.*;

@MessagingGateway(
    defaultRequestChannel = JOB_IMAGES_RESIZE_QUEUE,
    defaultReplyChannel = JOB_IMAGES_RESIZE_QUEUE+REPLY
)
public interface ImagesResizeJobBackendGateway {

    @Gateway(
        replyChannel = JOB_IMAGES_RESIZE_QUEUE+REPLY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String logbuch);

    void sendMessage(String payload, String category, JobCase job);
}
