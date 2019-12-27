package org.woehlke.tools.jobs.mq.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.*;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.woehlke.tools.config.LogbuchQueueServiceGateway;
import org.woehlke.tools.db.Logbuch;
import org.woehlke.tools.jobs.mq.LogbuchQueueService;
import org.woehlke.tools.db.LogbuchService;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.messaging.MessageHeaders.REPLY_CHANNEL;
import static org.woehlke.tools.config.SpringIntegrationConfig.LOGBUCH_QUEUE;
import static org.woehlke.tools.config.SpringIntegrationConfig.LOGBUCH_REPLY_QUEUE;


@Service("logbuchQueueService")
public class LogbuchQueueServiceImpl implements LogbuchQueueService, LogbuchQueueServiceGateway {

    private final LogbuchService logbuchService;
    private final MessageChannel logbuchChannel;

    private Log log = LogFactory.getLog(LogbuchQueueServiceImpl.class);

    @Autowired
    public LogbuchQueueServiceImpl(
        @Qualifier("logbuchService") LogbuchService logbuchService,
        @Qualifier(LOGBUCH_QUEUE) MessageChannel logbuchChannel
    ) {
        this.logbuchService = logbuchService;
        this.logbuchChannel = logbuchChannel;
    }

    @Override
    public void info(String msg) {
        String job ="DEFAULT_JOB";
        String category = "DEFAULT_CATEGORY";
        sendMessage(msg,category,job);
    }

    @Override
    public void info(String msg, String category, String job) {
        sendMessage(msg,category,job);
    }

    @Override
    public void info(String msg, String category) {
        String job ="DEFAULT_JOB";
        sendMessage(msg,category,job);
    }

    @Override
    public StringBuffer getInfo() {
        StringBuffer b = new StringBuffer();
        for(Logbuch p : logbuchService.getAll()){
            b.append(p.getLine());
            b.append("\n");
        }
        return b;
    }

    public void sendMessage(String payload, String category, String job) {
        MessagingTemplate template = new MessagingTemplate();
        Map<String, Object> headers = new HashMap<>();
        headers.put("output-channel", LOGBUCH_QUEUE);
        headers.put(REPLY_CHANNEL, LOGBUCH_REPLY_QUEUE);
        headers.put("my-category", category);
        headers.put("my-job", job);
        Message<String> msg = MessageBuilder.createMessage(payload,new MessageHeaders(headers));
        template.send(this.logbuchChannel,msg);
    }

    @Override
    public String listen(String logbuch) {
        return logbuch;
        //return logbuchService.add(logbuch);
    }
}
