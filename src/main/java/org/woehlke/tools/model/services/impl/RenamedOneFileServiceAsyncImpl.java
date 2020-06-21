package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.entities.RenamedOneFile;
import org.woehlke.tools.model.common.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.services.RenamedOneFileService;
import org.woehlke.tools.model.services.RenamedOneFileServiceAsync;

import static org.woehlke.tools.config.properties.ToolsQueueNames.JOB_RENAME_QUEUE;

@Service
public class RenamedOneFileServiceAsyncImpl extends JobEventServiceAsyncImpl<RenamedOneFile> implements RenamedOneFileServiceAsync {

    private final QueueChannel myQueueChannel;

    @Autowired
    public RenamedOneFileServiceAsyncImpl(
        RenamedOneFileService jobEventService,
        @Qualifier(JOB_RENAME_QUEUE)
        QueueChannel myQueueChannel
    ) {
        super(jobEventService);
        this.myQueueChannel = myQueueChannel;
    }

    protected QueueChannel getQueueChannel() {
        return myQueueChannel;
    }
}
