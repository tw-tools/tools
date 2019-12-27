package org.woehlke.tools.config.images;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.QuereNames.LOGBUCH_QUEUE;
import static org.woehlke.tools.config.QuereNames.LOGBUCH_REPLY_QUEUE;


@MessagingGateway(
    name = "myJobScaleImagesPanelGateway",
    defaultRequestChannel = LOGBUCH_QUEUE,
    defaultReplyChannel = LOGBUCH_REPLY_QUEUE
)
public interface JobScaleImagesPanelGateway {

    @Gateway(
        replyChannel = LOGBUCH_REPLY_QUEUE,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);
}
