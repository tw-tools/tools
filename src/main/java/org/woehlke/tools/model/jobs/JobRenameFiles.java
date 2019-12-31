package org.woehlke.tools.model.jobs;

import java.io.File;

public interface JobRenameFiles extends Runnable {

    void setRootDirectory(File rootDirectory);

    String getJobName();
}
