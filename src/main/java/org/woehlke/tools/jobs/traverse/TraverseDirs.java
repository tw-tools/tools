package org.woehlke.tools.jobs.traverse;

import org.woehlke.tools.model.entities.Job;

import java.io.File;
import java.io.FileFilter;
import java.util.Deque;

public interface TraverseDirs extends Runnable {

    void add(
        final Job job,
        final FileFilter filterFiles
    );

    Deque<File> getResult();
}
