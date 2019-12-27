package org.woehlke.tools.jobs.mq;

public interface LogbuchQueueService {

    void info(String msg);
    void info(String msg, String category);
    void info(String msg, String category, String job);

    void sendMessage(String payload, String category, String job);
    String listen(String logbuch);
}
