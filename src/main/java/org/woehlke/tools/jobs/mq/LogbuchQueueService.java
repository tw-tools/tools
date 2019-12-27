package org.woehlke.tools.jobs.mq;

import org.woehlke.tools.db.Logbuch;

public interface LogbuchQueueService {

    void info(String msg);
    void info(String msg, String category);
    void info(String msg, String category, String job);
    StringBuffer getInfo();

    void sendMessage(Logbuch logbuch);
    Logbuch listen(Logbuch logbuch);
}
