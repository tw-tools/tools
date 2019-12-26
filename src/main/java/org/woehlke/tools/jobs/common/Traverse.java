package org.woehlke.tools.jobs.common;

import java.io.File;
import java.util.Deque;

public interface Traverse extends Runnable {

    void add(final String dataRootDir, final boolean dryRun);

    String getDataRootDir();
    boolean isDryRun();
    Deque<File> getResult();
}
