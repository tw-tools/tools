package org.woehlke.tools.model.mq.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.*;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.woehlke.tools.config.mq.backend.JobRenameFilesBackendGateway;
import org.woehlke.tools.config.db.JobCase;
import org.woehlke.tools.model.mq.RenameQueue;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.messaging.MessageHeaders.REPLY_CHANNEL;
import static org.woehlke.tools.config.QueueNames.JOB_RENAME_FILES_QUEUE;
import static org.woehlke.tools.config.QueueNames.JOB_RENAME_FILES_QUEUE_REPLAY;


@Component
public class RenameBackendImpl implements RenameQueue,
    JobRenameFilesBackendGateway {

    private final MessageChannel renameFilesChannel;

    @Autowired
    public RenameBackendImpl(
        @Qualifier(JOB_RENAME_FILES_QUEUE) MessageChannel renameFilesChannel
     ) {
        this.renameFilesChannel = renameFilesChannel;
    }

    @Override
    public void sendMessage(String payload, String category, JobCase job) {
        MessagingTemplate template = new MessagingTemplate();
        Map<String, Object> headers = new HashMap<>();
        headers.put("output-channel", JOB_RENAME_FILES_QUEUE);
        headers.put(REPLY_CHANNEL, JOB_RENAME_FILES_QUEUE_REPLAY);
        headers.put("my-category", category);
        headers.put("my-job", job.toString());
        Message<String> msg = MessageBuilder.createMessage(
            payload,
            new MessageHeaders(headers)
        );
        template.send(this.renameFilesChannel,msg);
    }

    @Override
    public String listen(String logbuch) {
        return logbuch;
        //return logbuchService.add(logbuch);
    }

    private Log logger = LogFactory.getLog(RenameBackendImpl.class);
}
