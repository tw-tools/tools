package org.woehlke.tools.jobs.rename;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.properties.QueueNames.*;

@MessagingGateway(
    defaultRequestChannel = JOB_RENAME_FILES_QUEUE,
    defaultReplyChannel = JOB_RENAME_FILES_QUEUE_REPLY
)
public interface JobRenamePanelGateway {

    @Gateway(
        replyChannel = JOB_RENAME_FILES_QUEUE_REPLY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);
}
