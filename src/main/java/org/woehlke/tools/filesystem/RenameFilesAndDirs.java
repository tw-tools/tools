package org.woehlke.tools.filesystem;

import java.io.File;

public interface RenameFilesAndDirs extends Runnable {

    void setRootDirectory(File rootDirectory, boolean dryRun);
}
