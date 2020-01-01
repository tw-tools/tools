package org.woehlke.tools.jobs.traverse;

import java.io.File;
import java.io.FileFilter;
import java.util.Deque;

public interface TraverseDirs extends Runnable {

    void add(
        final String dataRootDir,
        final FileFilter filterFiles
    );

    Deque<File> getResult();
}
