package org.woehlke.tools.config.db;

public enum JobRenameEvent {


    SET_ROOT_DIRECTORY( "set Root Directory"),
    TRAVERSE_FILES( "traverse Files"),
    RENAME_DIRECTORIES("rename Directories"),
    RENAME_FILES ("rename Files"),
    RENAME_ONE_DIRECTORY( "rename One Directory"),
    RENAME_ONE_FILE ("rename One File"),
    TRAVERSE_DIRS( "traverse Directories");

    JobRenameEvent(String humanReadable){
        this.humanReadable = humanReadable;
    }

    private String humanReadable;

    public String getHumanReadable() {
        return humanReadable;
    }
}
