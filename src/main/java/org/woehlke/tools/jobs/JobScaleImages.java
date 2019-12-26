package org.woehlke.tools.jobs;

import java.io.File;

public interface JobScaleImages extends Runnable {

    void setRootDirectory(File rootDirectory, boolean dryRun);
}
