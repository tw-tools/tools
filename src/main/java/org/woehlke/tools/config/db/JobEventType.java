package org.woehlke.tools.config.db;

import static org.woehlke.tools.config.db.JobCase.*;

public enum JobEventType {

    LOGUBUCH_EVENT("Logbuch Event",ALL),
    SET_ROOT_DIRECTORY( "set Root Directory",ALL),
    TRAVERSE_DIRS( "traverse Directories",ALL),
    TRAVERSE_FILES( "traverse Files",ALL),

    RENAME_DIRECTORIES("rename Directories", JOB_RENAME_DIRECTORIES),
    RENAME_ONE_DIRECTORY( "rename One Directory", JOB_RENAME_DIRECTORIES),

    RENAME_FILES ("rename Files", JOB_RENAME_FILES),
    RENAME_ONE_FILE ("rename One File", JOB_RENAME_FILES),

    SCALE_JPG_IMAGES ("scale jpg images",JOB_SCALE_IMAGES),
    SCALE_ONE_JPG_IMAGE( "scale one image",JOB_SCALE_IMAGES);

    JobEventType(String humanReadable, JobCase jobCase){
        this.humanReadable = humanReadable;
    }

    private String humanReadable;
    private JobCase jobCase;

    public String getHumanReadable() {
        return humanReadable;
    }

    public JobCase getJobCase() {
        return jobCase;
    }
}
