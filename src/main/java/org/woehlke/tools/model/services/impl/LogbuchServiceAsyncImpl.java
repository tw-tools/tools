package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.entities.ToolsLogbuch;
import org.woehlke.tools.model.common.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.services.ToolsLogbuchService;
import org.woehlke.tools.model.services.LogbuchServiceAsync;

import static org.woehlke.tools.config.properties.ToolsQueueNames.*;

@Service
public class LogbuchServiceAsyncImpl extends JobEventServiceAsyncImpl<ToolsLogbuch> implements LogbuchServiceAsync {

    private final QueueChannel myQueueChannel;

    @Autowired
    public LogbuchServiceAsyncImpl(
        ToolsLogbuchService toolsLogbuchService,
        @Qualifier(LOGBUCH_QUEUE)
        QueueChannel myQueueChannel) {
        super(toolsLogbuchService);
        this.myQueueChannel = myQueueChannel;
    }

    protected QueueChannel getQueueChannel() {
        return myQueueChannel;
    }
}
