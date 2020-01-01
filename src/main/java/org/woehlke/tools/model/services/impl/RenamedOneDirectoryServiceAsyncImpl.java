package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.entities.RenamedOneDirectory;
import org.woehlke.tools.model.common.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.services.RenamedOneDirectoryService;
import org.woehlke.tools.model.services.RenamedOneDirectoryServiceAsync;

import static org.woehlke.tools.config.properties.QueueNames.JOB_RENAME_QUEUE;

@Service
public class RenamedOneDirectoryServiceAsyncImpl extends JobEventServiceAsyncImpl<RenamedOneDirectory> implements RenamedOneDirectoryServiceAsync {

    private final QueueChannel myQueueChannel;

    @Autowired
    public RenamedOneDirectoryServiceAsyncImpl(
        RenamedOneDirectoryService renamedOneDirectoryService,
        @Qualifier(JOB_RENAME_QUEUE)
        QueueChannel myQueueChannel
    ) {
       super(renamedOneDirectoryService);
        this.myQueueChannel = myQueueChannel;
    }

    protected QueueChannel getQueueChannel() {
        return myQueueChannel;
    }
}
