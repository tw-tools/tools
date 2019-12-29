package org.woehlke.tools.model.jobs.common;

import org.woehlke.tools.config.db.JobCase;

public interface LogbuchQueueService {

    void info(String msg);
    void info(String msg, String category);
    void info(String msg, String category, JobCase job);

    void sendMessage(String payload, String category, JobCase job);
    String listen(String logbuch);
}
