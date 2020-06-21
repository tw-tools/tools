package org.woehlke.tools.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.entities.ScaledImageJpg;
import org.woehlke.tools.model.common.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.services.ScaledImageJpgService;
import org.woehlke.tools.model.services.ScaledImageJpgServiceAsync;

import static org.woehlke.tools.config.properties.ToolsQueueNames.JOB_IMAGES_RESIZE_QUEUE;

@Service
public class ScaledImageJpgServiceAsyncImpl extends JobEventServiceAsyncImpl<ScaledImageJpg> implements ScaledImageJpgServiceAsync {

    private final QueueChannel myQueueChannel;

    @Autowired
    public ScaledImageJpgServiceAsyncImpl(
        ScaledImageJpgService jobEventService,
        @Qualifier(JOB_IMAGES_RESIZE_QUEUE)
        QueueChannel myQueueChannel
    ) {
        super(jobEventService);
        this.myQueueChannel = myQueueChannel;
    }

    protected QueueChannel getQueueChannel() {
        return myQueueChannel;
    }
}
