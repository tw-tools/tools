package org.woehlke.tools.model.jobs.images.mq.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.woehlke.tools.config.ToolsApplicationProperties;
import org.woehlke.tools.model.jobs.images.mq.JobScaleImagesQueueGateway;
import org.woehlke.tools.model.db.config.JobCase;
import org.woehlke.tools.model.jobs.images.mq.JobScaleImagesQueue;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.messaging.MessageHeaders.REPLY_CHANNEL;
import static org.woehlke.tools.config.QueueNames.*;


@Service("jobScaleImagesQueueImpl")
public class JobScaleImagesQueueImpl implements JobScaleImagesQueue, JobScaleImagesQueueGateway {

    private final MessageChannel imagesChannel;
    private final ToolsApplicationProperties properties;

    private Log logger = LogFactory.getLog(JobScaleImagesQueueImpl.class);

    @Autowired
    public JobScaleImagesQueueImpl(
        @Qualifier(SCALE_IMAGES_QUEUE) MessageChannel imagesChannel,
        ToolsApplicationProperties properties) {
        this.imagesChannel = imagesChannel;
        this.properties = properties;
    }

    @Override
    public void info(String msg) {
        JobCase job = JobCase.JOB_SCALE_IMAGES;
        String category = "DEFAULT_CATEGORY";
        sendMessage(msg,category,job);
    }

    @Override
    public void info(String msg, String category, JobCase job) {
        sendMessage(msg,category,job);
    }

    @Override
    public void info(String msg, String category) {
        JobCase job = JobCase.JOB_SCALE_IMAGES;
        sendMessage(msg,category,job);
    }

    public void sendMessage(String payload, String category, JobCase job) {
        MessagingTemplate template = new MessagingTemplate();
        Map<String, Object> headers = new HashMap<>();
        headers.put("output-channel", SCALE_IMAGES_QUEUE);
        headers.put(REPLY_CHANNEL, SCALE_IMAGES_QUEUE_REPLAY);
        headers.put("my-category", category);
        headers.put("my-job", JobCase.JOB_SCALE_IMAGES.toString());
        Message<String> msg = MessageBuilder.createMessage(payload,new MessageHeaders(headers));
        template.send(this.imagesChannel,msg);
    }

    @Override
    public String listen(String logbuch) {
        return logbuch;
        //return logbuchService.add(logbuch);
    }
}
