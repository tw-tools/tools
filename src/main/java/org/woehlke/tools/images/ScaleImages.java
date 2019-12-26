package org.woehlke.tools.images;

import java.io.File;

public interface ScaleImages extends Runnable {

    void setRootDirectory(File rootDirectory, boolean dryRun);
}
