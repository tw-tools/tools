package org.woehlke.tools.config.mq;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.QueueNames.*;

@MessagingGateway(
    name = "myRenameFilesPanelGateway",
    defaultRequestChannel = JOB_RENAME_FILES_QUEUE,
    defaultReplyChannel = JOB_RENAME_FILES_QUEUE_REPLAY
)
public interface JobRenameFilesPanelGateway {

    @Gateway(
        replyChannel = JOB_RENAME_FILES_QUEUE_REPLAY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);
}
