package org.woehlke.tools.jobs.rename.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.*;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.woehlke.tools.jobs.common.impl.AbstractJobGroupImpl;
import org.woehlke.tools.jobs.rename.JobRenameBackendGateway;
import org.woehlke.tools.model.db.config.JobCase;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.messaging.MessageHeaders.REPLY_CHANNEL;
import static org.woehlke.tools.config.properties.QueueNames.*;


@Component("jobRenameFilesBackendGatewayImpl")
public class JobRenameBackendGatewayImpl implements JobRenameBackendGateway {

    private final MessageChannel renameFilesChannel;

    @Autowired
    public JobRenameBackendGatewayImpl(
        @Qualifier(JOB_RENAME_QUEUE) MessageChannel renameFilesChannel
     ) {
        this.renameFilesChannel = renameFilesChannel;
    }

    @Override
    public void sendMessage(String payload, String category, JobCase job) {
        MessagingTemplate template = new MessagingTemplate();
        Map<String, Object> headers = new HashMap<>();
        headers.put("output-channel", JOB_RENAME_QUEUE);
        headers.put(REPLY_CHANNEL, JOB_RENAME_QUEUE+REPLY);
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

    private Log logger = LogFactory.getLog(JobRenameBackendGatewayImpl.class);

}
