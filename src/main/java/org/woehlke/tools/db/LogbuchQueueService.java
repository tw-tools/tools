package org.woehlke.tools.db;

import org.woehlke.tools.db.Logbuch;

public interface LogbuchQueueService {

    void info(String msg);
    void info(String msg, String category);
    void info(String msg, String category, String job);
    StringBuffer getInfo();

    void receiveMessage(Logbuch logbuch);
    void sendMessage(Logbuch logbuch);

}
