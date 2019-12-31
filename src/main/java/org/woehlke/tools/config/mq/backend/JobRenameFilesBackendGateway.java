package org.woehlke.tools.config.mq.backend;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.QueueNames.*;

@MessagingGateway(
    name = "myJobRenameFilesBackendGateway",
    defaultRequestChannel = JOB_RENAME_FILES_QUEUE,
    defaultReplyChannel = JOB_RENAME_FILES_QUEUE_REPLAY
)
public interface JobRenameFilesBackendGateway {

    @Gateway(
        replyChannel = JOB_RENAME_FILES_QUEUE_REPLAY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String logbuch);
}
