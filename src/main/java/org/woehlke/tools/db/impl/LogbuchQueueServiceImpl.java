package org.woehlke.tools.db.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woehlke.tools.db.Logbuch;
import org.woehlke.tools.db.LogbuchQueueService;
import org.woehlke.tools.db.LogbuchService;

import static org.woehlke.tools.config.SpringIntegrationConfig.LOGBUCH_QUEUE;


@Service
public class LogbuchQueueServiceImpl implements LogbuchQueueService {

    private final LogbuchService logbuchService;

    private Log log = LogFactory.getLog(LogbuchQueueServiceImpl.class);

    @Autowired
    public LogbuchQueueServiceImpl(
        LogbuchService logbuchService
    ) {
        this.logbuchService = logbuchService;
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

    public void sendMessage(Logbuch logbuch) {
        //String routingKey = LOGBUCH_ROUTING_KEY;
        //String msg ="send Message to key " + routingKey + " msg = " + logbuch.toString();
        //log.info(msg);
        //amqpTemplate.convertAndSend(routingKey,logbuch);
    }

    public void receiveMessage(Logbuch logbuch) {
        String msg ="received Message from Queue " + LOGBUCH_QUEUE + " msg = " + logbuch.toString();
        log.info(msg);
        logbuchService.add(logbuch);
    }

}
