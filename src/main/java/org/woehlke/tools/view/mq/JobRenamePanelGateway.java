package org.woehlke.tools.view.mq;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import static org.woehlke.tools.config.properties.ToolsQueueNames.*;

@MessagingGateway(
    defaultRequestChannel = JOB_RENAME_QUEUE,
    defaultReplyChannel = JOB_RENAME_QUEUE+REPLY
)
public interface JobRenamePanelGateway {

    @Gateway(
        replyChannel = JOB_RENAME_QUEUE+REPLY,
        replyTimeout = 2,
        requestTimeout = 200
    )
    String listen(String payload);
}
