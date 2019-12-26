package org.woehlke.tools.db.service.impl;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.db.entity.Logbuch;
import org.woehlke.tools.db.service.LogbuchQueueService;
import org.woehlke.tools.db.service.LogbuchService;

import static org.woehlke.tools.config.ApplicationConfig.LOGBUCH_QUEUE;
import static org.woehlke.tools.config.ApplicationConfig.LOGBUCH_ROUTING_KEY;


@Service
public class LogbuchQueueServiceImpl implements LogbuchQueueService {

    private final LogbuchService logbuchService;
    private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;
    private final Queue queue;

    @Autowired
    public LogbuchQueueServiceImpl(LogbuchService logbuchService, AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate, Queue queue) {
        this.logbuchService = logbuchService;
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
        this.queue = queue;
    }

    @RabbitListener(queues = LOGBUCH_QUEUE)
    public void receiveMessage(Logbuch logbuch) {
        logbuchService.add(logbuch);
    }

    public void sendMessage(Logbuch logbuch) {
        String routingKey = LOGBUCH_ROUTING_KEY;
        Object response =  amqpTemplate.convertSendAndReceive(routingKey,logbuch);
    }

    @Override
    public void info(String msg) {
        String textAreaMsg = msg;
        Logbuch newLogbuch = new Logbuch(textAreaMsg);
        sendMessage(newLogbuch);
    }

    @Override
    public void info(String msg, String category, String job) {
        String textAreaMsg = msg;
        Logbuch newLogbuch = new Logbuch(textAreaMsg);
        newLogbuch.setCategory(category);
        newLogbuch.setJob(job);
        sendMessage(newLogbuch);
    }

    @Override
    public void info(String msg, String category) {
        String textAreaMsg = msg;
        Logbuch newLogbuch = new Logbuch(textAreaMsg);
        newLogbuch.setCategory(category);
        sendMessage(newLogbuch);
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
}
