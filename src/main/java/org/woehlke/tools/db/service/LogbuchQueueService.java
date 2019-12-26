package org.woehlke.tools.db.service;

public interface LogbuchQueueService {

    void info(String msg);

    void info(String msg, String category, String job);

    void info(String msg, String category);

    StringBuffer getInfo();
}
