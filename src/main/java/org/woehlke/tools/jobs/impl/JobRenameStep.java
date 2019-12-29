package org.woehlke.tools.jobs.impl;

public enum JobRenameStep {

    RENAME_DIRECTORIES("rename Directories"),
    RENAME_FILES ("rename Files"),
    RENAME_ONE_FILE ("rename One File"),
    RENAME( "rename"),
    TRAVERSE_DIRS( "traverse Dirs"),
    TRAVERSE_FILES( "traverse Files"),
    SET_ROOT_DIRECTORY( "set Root Directory");

    JobRenameStep(String humanReadable){
        this.humanReadable = humanReadable;
    }

    private String humanReadable;

    public String getHumanReadable() {
        return humanReadable;
    }
}
