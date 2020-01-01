package org.woehlke.tools.view.mq;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.properties.QueueNames.*;

@MessagingGateway(
    defaultRequestChannel = LOGBUCH_QUEUE,
    defaultReplyChannel = LOGBUCH_QUEUE+REPLY
)
public interface LogbuchPanelGateway {

    @Gateway(
        replyChannel = LOGBUCH_QUEUE+REPLY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);
}
