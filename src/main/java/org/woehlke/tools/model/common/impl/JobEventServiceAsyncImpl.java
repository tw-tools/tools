package org.woehlke.tools.model.common.impl;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.woehlke.tools.model.common.JobEvent;
import org.woehlke.tools.model.common.JobEventService;
import org.woehlke.tools.model.common.JobEventServiceAsync;
import org.woehlke.tools.model.config.JobCase;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.messaging.MessageHeaders.REPLY_CHANNEL;
import static org.woehlke.tools.config.properties.ToolsQueueNames.JOB_IMAGES_RESIZE_QUEUE;
import static org.woehlke.tools.config.properties.ToolsQueueNames.REPLY;

@NoRepositoryBean
public abstract class JobEventServiceAsyncImpl<T extends JobEvent> implements JobEventServiceAsync<T> {

    private final JobEventService<T> jobEventService;

    public JobEventServiceAsyncImpl(JobEventService<T> jobEventService) {
        this.jobEventService = jobEventService;
    }

    @Async
    @Override
    public T add(T p) {
        return this.jobEventService.add(p);
    }

    protected abstract QueueChannel getQueueChannel();

    public void sendMessage(T p) {
        MessagingTemplate template = new MessagingTemplate();
        MessageHeaders headers = getHeaders(p.getCategory(), p.getJob().getJobCase());
        Message<String> msg = MessageBuilder.createMessage(
            p.getLine(), headers
        );
        template.send(getQueueChannel(),msg);
    }

    protected MessageHeaders getHeaders(String category, JobCase jobCase){
        Map<String, Object> headers = new HashMap<>();
        headers.put("output-channel", JOB_IMAGES_RESIZE_QUEUE);
        headers.put(REPLY_CHANNEL, JOB_IMAGES_RESIZE_QUEUE+REPLY);
        headers.put("my-category", category);
        headers.put("my-job-defined", JobCase.JOB_IMAGES_RESIZE.name());
        headers.put("my-job-given", jobCase.name());
        return new MessageHeaders(headers);
    }
}
