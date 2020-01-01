package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.config.JobCase;
import org.woehlke.tools.model.entities.Logbuch;
import org.woehlke.tools.model.common.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.services.LogbuchService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.messaging.MessageHeaders.REPLY_CHANNEL;
import static org.woehlke.tools.config.properties.QueueNames.*;

@Service
public class LogbuchServiceAsyncImpl extends JobEventServiceAsyncImpl<Logbuch> implements LogbuchServiceAsync {

    private final QueueChannel myQueueChannel;

    @Autowired
    public LogbuchServiceAsyncImpl(
        LogbuchService logbuchService,
        @Qualifier(LOGBUCH_QUEUE)
        QueueChannel myQueueChannel) {
        super(logbuchService);
        this.myQueueChannel = myQueueChannel;
    }

    protected QueueChannel getQueueChannel() {
        return myQueueChannel;
    }
}
