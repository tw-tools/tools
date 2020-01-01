package org.woehlke.tools.model.mq;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.woehlke.tools.config.db.JobCase;

import static org.woehlke.tools.config.QueueNames.LOGBUCH_QUEUE;
import static org.woehlke.tools.config.QueueNames.LOGBUCH_QUEUE_REPLAY;

@MessagingGateway(
    defaultRequestChannel = LOGBUCH_QUEUE,
    defaultReplyChannel = LOGBUCH_QUEUE_REPLAY
)
public interface LogbuchBackendGateway {

    @Gateway(
        replyChannel = LOGBUCH_QUEUE_REPLAY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);

    void sendMessage(String payload, String category, JobCase job);
}
