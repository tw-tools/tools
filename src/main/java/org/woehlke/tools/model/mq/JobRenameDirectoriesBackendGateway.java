package org.woehlke.tools.model.mq;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.woehlke.tools.config.db.JobCase;

import static org.woehlke.tools.config.QueueNames.JOB_RENAME_DIRECTORIES_QUEUE;
import static org.woehlke.tools.config.QueueNames.JOB_RENAME_DIRECTORIES_QUEUE_REPLAY;

@MessagingGateway(
    defaultRequestChannel = JOB_RENAME_DIRECTORIES_QUEUE,
    defaultReplyChannel = JOB_RENAME_DIRECTORIES_QUEUE_REPLAY
)
public interface JobRenameDirectoriesBackendGateway {

    @Gateway(
        replyChannel = JOB_RENAME_DIRECTORIES_QUEUE_REPLAY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);

    void sendMessage(String payload, String category, JobCase job);
}
