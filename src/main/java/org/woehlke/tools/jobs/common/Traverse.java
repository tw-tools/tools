package org.woehlke.tools.jobs.common;

import org.woehlke.tools.jobs.mq.LogbuchQueueService;

import java.io.File;
import java.util.Deque;

public interface Traverse extends Runnable {

    void add(final String dataRootDir, LogbuchQueueService log);

    Deque<File> getResult();
}
