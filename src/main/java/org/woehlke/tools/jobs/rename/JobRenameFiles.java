package org.woehlke.tools.jobs.rename;

import java.io.File;

public interface JobRenameFiles extends Runnable {

    void setRootDirectory(File rootDirectory);

    String getJobName();
}
