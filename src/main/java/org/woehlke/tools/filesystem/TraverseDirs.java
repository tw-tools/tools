package org.woehlke.tools.filesystem;

import org.springframework.stereotype.Component;
import org.woehlke.tools.filesystem.impl.FileFilterDirectory;
import org.woehlke.tools.view.LoggingCallback;

import java.io.File;
import java.util.*;

@Component("traverseDirs")
public class TraverseDirs implements Traverse {

    private String dataRootDir;
    private boolean dryRun;
    private final Deque<File> result = new ArrayDeque<File>();

    private LoggingCallback log;

    private final FileFilterDirectory filter;

    public TraverseDirs() {
        this.filter = new FileFilterDirectory();
    }

    public void add(String dataRootDir,final boolean dryRun,LoggingCallback log) {
        this.dataRootDir = dataRootDir;
        this.dryRun = dryRun;
        this.log = log;
    }

    @Override
    public void run() {
        File dataRoot = new File(dataRootDir);
        File subdirs[] = {dataRoot};
        traverseSubDirs(subdirs);
    }

    private void traverseSubDirs(File subdirs[]){
        for(File subdir:subdirs) {
            if (subdir.isDirectory()) {
                result.push(subdir);
                log.info("cd " +subdir.getAbsolutePath());
                File nextsubdirs[] = subdir.listFiles(filter);
                traverseSubDirs(nextsubdirs);
            }
        }
        log.info("cd ..");
    }

    public String getDataRootDir() {
        return dataRootDir;
    }

    public Deque<File> getResult() {
        return result;
    }

    public boolean isDryRun() {
        return dryRun;
    }
}
