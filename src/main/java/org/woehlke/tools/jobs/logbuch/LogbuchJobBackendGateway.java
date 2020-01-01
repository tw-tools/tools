package org.woehlke.tools.jobs.logbuch;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.woehlke.tools.model.config.JobCase;

import static org.woehlke.tools.config.properties.QueueNames.LOGBUCH_QUEUE;
import static org.woehlke.tools.config.properties.QueueNames.REPLY;

@MessagingGateway(
    defaultRequestChannel = LOGBUCH_QUEUE,
    defaultReplyChannel = LOGBUCH_QUEUE+REPLY
)
public interface LogbuchJobBackendGateway {

    @Gateway(
        replyChannel = LOGBUCH_QUEUE+REPLY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);

    void sendMessage(String payload, String category, JobCase job);
}
