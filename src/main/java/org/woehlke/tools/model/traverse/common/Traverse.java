package org.woehlke.tools.model.traverse.common;

import org.woehlke.tools.model.jobs.common.LogbuchQueueService;

import java.io.File;
import java.io.FileFilter;
import java.util.Deque;

public interface Traverse extends Runnable {

    void add(final String dataRootDir, final LogbuchQueueService log, final FileFilter filterFiles);

    Deque<File> getResult();
}
