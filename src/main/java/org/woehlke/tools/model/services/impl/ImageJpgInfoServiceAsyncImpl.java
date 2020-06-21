package org.woehlke.tools.model.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.stereotype.Service;
import org.woehlke.tools.model.entities.ImageJpgInfo;
import org.woehlke.tools.model.common.impl.JobEventServiceAsyncImpl;
import org.woehlke.tools.model.services.ImageJpgInfoService;
import org.woehlke.tools.model.services.ImageJpgInfoServiceAsync;

import static org.woehlke.tools.config.properties.QueueNames.JOB_IMAGES_INFO_QUEUE;

@Service
public class ImageJpgInfoServiceAsyncImpl extends JobEventServiceAsyncImpl<ImageJpgInfo>
    implements ImageJpgInfoServiceAsync {

    private final QueueChannel myQueueChannel;

    @Autowired
    public ImageJpgInfoServiceAsyncImpl(
        ImageJpgInfoService imageJpgInfoService,
        @Qualifier(JOB_IMAGES_INFO_QUEUE)
        QueueChannel infoImagesChannel) {
        super(imageJpgInfoService);
        this.myQueueChannel = infoImagesChannel;
    }

    protected QueueChannel getQueueChannel() {
        return myQueueChannel;
    }

}
