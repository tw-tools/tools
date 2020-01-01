package org.woehlke.tools.jobs.rename;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.woehlke.tools.model.config.JobCase;

import static org.woehlke.tools.config.properties.QueueNames.*;

@MessagingGateway(
    defaultRequestChannel = JOB_RENAME_QUEUE,
    defaultReplyChannel = JOB_RENAME_QUEUE+REPLY
)
public interface RenameJobBackendGateway {

    @Gateway(
        replyChannel = JOB_RENAME_QUEUE+REPLY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String logbuch);

    void sendMessage(String payload, String category, JobCase job);
}
