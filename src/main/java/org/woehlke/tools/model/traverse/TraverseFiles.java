package org.woehlke.tools.model.traverse;

import java.io.File;
import java.io.FileFilter;
import java.util.Deque;

public interface TraverseFiles extends Runnable {

    void add(
        final String dataRootDir,
        final FileFilter filterFiles
    );

    Deque<File> getResult();
}
