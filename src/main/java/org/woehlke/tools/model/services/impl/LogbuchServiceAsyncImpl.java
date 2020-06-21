package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.entities.Logbuch;
import org.woehlke.tools.model.common.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.services.LogbuchService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;

import static org.woehlke.tools.config.properties.ToolsQueueNames.*;

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
