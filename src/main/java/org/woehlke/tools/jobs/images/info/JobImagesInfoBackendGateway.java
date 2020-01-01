package org.woehlke.tools.jobs.images.info;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.woehlke.tools.jobs.config.JobCase;

import static org.woehlke.tools.config.properties.QueueNames.JOB_IMAGES_INFO_QUEUE;
import static org.woehlke.tools.config.properties.QueueNames.JOB_IMAGES_INFO_QUEUE_REPLY;

@MessagingGateway(
    defaultRequestChannel = JOB_IMAGES_INFO_QUEUE,
    defaultReplyChannel = JOB_IMAGES_INFO_QUEUE_REPLY
)
public interface JobImagesInfoBackendGateway {

    @Gateway(
        replyChannel = JOB_IMAGES_INFO_QUEUE_REPLY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);

    void sendMessage(String payload, String category, JobCase job);
}
