package org.woehlke.tools.config.mq;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.QueueNames.*;

@MessagingGateway(
    name = "myLogbuchPanelGateway",
    defaultRequestChannel = LOGBUCH_QUEUE,
    defaultReplyChannel = LOGBUCH_QUEUE_REPLAY
)
public interface LogbuchPanelGateway {

    @Gateway(
        replyChannel = LOGBUCH_QUEUE_REPLAY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);
}
