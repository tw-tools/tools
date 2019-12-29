package org.woehlke.tools.jobs.impl;

import org.woehlke.tools.db.common.JobCase;

public enum JobRenameStep {

    RENAME_DIRECTORIES("rename Directories"),
    RENAME_FILES ("rename Files"),
    RENAME_ONE_FILE ("rename One File"),
    RENAME( "rename"),
    TRAVERSE_DIRS( "traverse Dirs"),
    TRAVERSE_FILES( "traverse Files"),
    SET_ROOT_DIRECTORY( "setRootDirectory"),
    JOB(JobCase.JOB_RENAME_FILES.getHumanReadable());

    JobRenameStep(String humanReadable){
        this.humanReadable = humanReadable;
    }

    private String humanReadable;

    public String getHumanReadable() {
        return humanReadable;
    }
}
