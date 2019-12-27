package org.woehlke.tools.db;

public interface LogbuchQueueService {

    void info(String msg);
    void info(String msg, String category);
    void info(String msg, String category, String job);
    StringBuffer getInfo();

    void sendMessage(Logbuch logbuch);
    Logbuch addLogbuch(Logbuch logbuch);
}
