package org.woehlke.tools.model.jobs.rename.mq.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.QueueNames.*;

@MessagingGateway(
    name = "myRenameFilesPanelGateway",
    defaultRequestChannel = RENAME_FILES_QUEUE,
    defaultReplyChannel = RENAME_FILES_QUEUE_REPLY
)
public interface JobRenameFilesPanelGateway {

    @Gateway(
        replyChannel = RENAME_FILES_QUEUE_REPLY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);
}
