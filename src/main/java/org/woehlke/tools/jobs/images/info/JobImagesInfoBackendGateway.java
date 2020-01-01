package org.woehlke.tools.jobs.images.info;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.woehlke.tools.model.db.config.JobCase;

import static org.woehlke.tools.config.properties.QueueNames.*;

@MessagingGateway(
    defaultRequestChannel = JOB_IMAGES_INFO_QUEUE,
    defaultReplyChannel = JOB_IMAGES_INFO_QUEUE+REPLY
)
public interface JobImagesInfoBackendGateway {

    @Gateway(
        replyChannel = JOB_IMAGES_INFO_QUEUE+REPLY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);

    void sendMessage(String payload, String category, JobCase job);
}
