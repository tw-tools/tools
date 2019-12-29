package org.woehlke.tools.jobs.mq.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.*;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.woehlke.tools.config.ToolsApplicationProperties;
import org.woehlke.tools.config.rename.JobRenameFilesQueueGateway;
import org.woehlke.tools.db.common.JobCase;
import org.woehlke.tools.jobs.mq.JobRenameFilesQueue;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.messaging.MessageHeaders.REPLY_CHANNEL;
import static org.woehlke.tools.config.QueueNames.*;


@Service("jobRenameFilesQueueImpl")
public class JobRenameFilesQueueQueueImpl implements JobRenameFilesQueue, JobRenameFilesQueueGateway {

    private final MessageChannel renameChannel;
    private final ToolsApplicationProperties toolsApplicationProperties;

    private Log logger = LogFactory.getLog(JobRenameFilesQueueQueueImpl.class);

    @Autowired
    public JobRenameFilesQueueQueueImpl(
        @Qualifier(RENAME_FILES_QUEUE) MessageChannel renameChannel,
        ToolsApplicationProperties toolsApplicationProperties) {
        this.renameChannel = renameChannel;
        this.toolsApplicationProperties = toolsApplicationProperties;
    }

    @Override
    public void info(String msg) {
        JobCase job = JobCase.JOB_RENAME_FILES;
        String category = "DEFAULT_CATEGORY";
        sendMessage(msg,category,job);
    }

    @Override
    public void info(String msg, String category, JobCase job) {
        sendMessage(msg,category,job);
    }

    @Override
    public void info(String msg, String category) {
        JobCase job = JobCase.JOB_RENAME_FILES;
        sendMessage(msg,category,job);
    }

    public void sendMessage(String payload, String category, JobCase job) {
        MessagingTemplate template = new MessagingTemplate();
        Map<String, Object> headers = new HashMap<>();
        headers.put("output-channel", RENAME_FILES_QUEUE);
        headers.put(REPLY_CHANNEL, RENAME_FILES_QUEUE_REPLY);
        headers.put("my-category", category);
        headers.put("my-job", job.toString());
        Message<String> msg = MessageBuilder.createMessage(payload,new MessageHeaders(headers));
        template.send(this.renameChannel,msg);
    }

    @Override
    public String listen(String logbuch) {
        return logbuch;
        //return logbuchService.add(logbuch);
    }
}
