package org.woehlke.tools.db.service.impl;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.db.entity.Logbuch;
import org.woehlke.tools.db.service.DbLogger;
import org.woehlke.tools.db.service.ProtokollService;

import static org.woehlke.tools.config.ApplicationConfig.QUEUE_NAME;

@Service
public class DbLoggerImpl implements DbLogger {

    private final ProtokollService protokollService;
    private final AmqpAdmin amqpAdmin;
    private final AmqpTemplate amqpTemplate;
    private final Queue queue;

    @Autowired
    public DbLoggerImpl(ProtokollService protokollService, AmqpAdmin amqpAdmin, AmqpTemplate amqpTemplate, Queue queue) {
        this.protokollService = protokollService;
        this.amqpAdmin = amqpAdmin;
        this.amqpTemplate = amqpTemplate;
        this.queue = queue;
    }


    @RabbitListener(queues = QUEUE_NAME)
    public void receiveMessage(String content) {
        // ...
    }

    public void sendMessage(String content) {
        // ...
    }

    @Override
    public void info(String msg) {
        String textAreaMsg = msg;
        Logbuch newLogbuch = new Logbuch(textAreaMsg);
        protokollService.add(newLogbuch);
    }

    @Override
    public void info(String msg, String category, String job) {
        String textAreaMsg = msg;
        Logbuch newLogbuch = new Logbuch(textAreaMsg);
        newLogbuch.setCategory(category);
        newLogbuch.setJob(job);
        protokollService.add(newLogbuch);
    }

    @Override
    public void info(String msg, String category) {
        String textAreaMsg = msg;
        Logbuch newLogbuch = new Logbuch(textAreaMsg);
        newLogbuch.setCategory(category);
        protokollService.add(newLogbuch);
    }

    @Override
    public StringBuffer getInfo() {
        StringBuffer b = new StringBuffer();
        for(Logbuch p :protokollService.getAll()){
            b.append(p.getLine());
            b.append("\n");
        }
        return b;
    }
}
