package org.woehlke.tools.jobs.rename;

import org.woehlke.tools.model.db.entities.Job;

public interface JobRenameFiles extends Runnable {

    void setRootDirectory(Job job);

    String getJobName();
}
