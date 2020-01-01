package org.woehlke.tools.jobs.images.info.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.woehlke.tools.model.config.JobCase;
import org.woehlke.tools.jobs.images.info.JobImagesInfoBackendGateway;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.messaging.MessageHeaders.REPLY_CHANNEL;
import static org.woehlke.tools.config.properties.PipelineNames.JOB_IMAGES_INFO_BACKEND_GATEWAY_IMPL;
import static org.woehlke.tools.config.properties.QueueNames.*;

@Component(JOB_IMAGES_INFO_BACKEND_GATEWAY_IMPL)
public class JobImagesInfoBackendGatewayImpl implements JobImagesInfoBackendGateway {

    @Autowired
    @Qualifier(JOB_IMAGES_INFO_QUEUE)
    private QueueChannel imagesInfoChannel;

    private MessageHeaders getHeaders(
        String category,
        JobCase jobCaseDefined,
        JobCase jobCaseGiven
    ){
        Map<String, Object> headers = new HashMap<>();
        headers.put("output-channel", jobCaseDefined.getQueue());
        headers.put(REPLY_CHANNEL, jobCaseDefined.getQueueReply());
        headers.put("my-category", category);
        headers.put("my-job-defined", jobCaseDefined.name());
        headers.put("my-job-given", jobCaseGiven.name());
        headers.put("my-output-channel", jobCaseDefined.getQueue());
        headers.put("my-"+REPLY_CHANNEL, jobCaseDefined.getQueueReply());
        return new MessageHeaders(headers);
    }

    public void sendMessage(String payload, String category, JobCase jobCase) {
        JobCase jobCaseDefined = JobCase.JOB_IMAGES_INFO_JPG;
        MessagingTemplate template = new MessagingTemplate();
        MessageHeaders headers = getHeaders(category, jobCaseDefined, jobCase);
        Message<String> msg = MessageBuilder.createMessage(
            payload,headers
        );
        template.send(this.imagesInfoChannel,msg);
    }

    @Override
    public String listen(String payload) {
        return payload;
    }
}
