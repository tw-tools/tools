package org.woehlke.tools.config.mq.backend;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.QueueNames.LOGBUCH_QUEUE;
import static org.woehlke.tools.config.QueueNames.LOGBUCH_QUEUE_REPLAY;

@MessagingGateway(
    name = "myLogbuchPanelGateway",
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
}
