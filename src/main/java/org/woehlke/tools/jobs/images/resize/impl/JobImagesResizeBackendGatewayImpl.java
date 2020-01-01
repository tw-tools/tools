package org.woehlke.tools.jobs.images.resize.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.woehlke.tools.jobs.config.JobCase;
import org.woehlke.tools.jobs.images.resize.JobImagesResizeBackendGateway;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.messaging.MessageHeaders.REPLY_CHANNEL;
import static org.woehlke.tools.config.properties.QueueNames.JOB_IMAGES_RESIZE_QUEUE;
import static org.woehlke.tools.config.properties.QueueNames.JOB_IMAGES_RESIZE_QUEUE_REPLY;


@Component("jobImagesResizeBackendGatewayImpl")
public class JobImagesResizeBackendGatewayImpl implements JobImagesResizeBackendGateway {

    @Autowired
    @Qualifier(JOB_IMAGES_RESIZE_QUEUE)
    private QueueChannel imagesResizeChannel;

    public void sendMessage(String payload, String category, JobCase job) {
        MessagingTemplate template = new MessagingTemplate();
        Map<String, Object> headers = new HashMap<>();
        headers.put("output-channel", JOB_IMAGES_RESIZE_QUEUE);
        headers.put(REPLY_CHANNEL, JOB_IMAGES_RESIZE_QUEUE_REPLY);
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
