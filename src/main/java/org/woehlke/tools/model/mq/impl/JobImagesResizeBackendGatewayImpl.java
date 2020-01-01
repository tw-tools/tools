package org.woehlke.tools.model.mq.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.db.JobCase;
import org.woehlke.tools.model.mq.JobImagesResizeBackendGateway;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.messaging.MessageHeaders.REPLY_CHANNEL;
import static org.woehlke.tools.config.QueueNames.JOB_RESIZE_IMAGES_QUEUE;
import static org.woehlke.tools.config.QueueNames.JOB_RESIZE_IMAGES_QUEUE_REPLAY;


@Component
public class JobImagesResizeBackendGatewayImpl implements JobImagesResizeBackendGateway {

    private final MessageChannel imagesResizeChannel;

    @Autowired
    public JobImagesResizeBackendGatewayImpl(
        @Qualifier(JOB_RESIZE_IMAGES_QUEUE) MessageChannel imagesResizeChannel
    ) {
        this.imagesResizeChannel = imagesResizeChannel;
    }

    public void sendMessage(String payload, String category, JobCase job) {
        MessagingTemplate template = new MessagingTemplate();
        Map<String, Object> headers = new HashMap<>();
        headers.put("output-channel", JOB_RESIZE_IMAGES_QUEUE);
        headers.put(REPLY_CHANNEL, JOB_RESIZE_IMAGES_QUEUE_REPLAY);
        headers.put("my-category", category);
        headers.put("my-job", JobCase.JOB_SCALE_IMAGES.toString());
        Message<String> msg = MessageBuilder.createMessage(
            payload,
            new MessageHeaders(headers)
        );
        template.send(this.imagesResizeChannel,msg);
    }

    @Override
    public String listen(String logbuch) {
        return logbuch;
    }

    private Log logger = LogFactory.getLog(JobImagesResizeBackendGatewayImpl.class);
}
