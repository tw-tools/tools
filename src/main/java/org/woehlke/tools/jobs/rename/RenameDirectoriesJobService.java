package org.woehlke.tools.jobs.rename;

import org.woehlke.tools.model.entities.Job;

public interface RenameDirectoriesJobService extends Runnable {

    void setRootDirectory(Job job);

    String getJobName();
}
