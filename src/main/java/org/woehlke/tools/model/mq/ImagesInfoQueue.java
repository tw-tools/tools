package org.woehlke.tools.model.mq;

import org.woehlke.tools.config.db.JobCase;

public interface ImagesInfoQueue {

    void sendMessage(String payload, String category, JobCase job);

    String listen(String payload);
}
