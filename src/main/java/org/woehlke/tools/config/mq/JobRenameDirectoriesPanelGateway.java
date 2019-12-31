package org.woehlke.tools.config.mq;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.QueueNames.*;

@MessagingGateway(
    name = "myJobRenameDirectoriesPanelGateway",
    defaultRequestChannel = JOB_RENAME_DIRECTORIES_QUEUE,
    defaultReplyChannel = JOB_RENAME_DIRECTORIES_QUEUE_REPLAY
)
public interface JobRenameDirectoriesPanelGateway {

    @Gateway(
        replyChannel = JOB_RENAME_DIRECTORIES_QUEUE_REPLAY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);
}
