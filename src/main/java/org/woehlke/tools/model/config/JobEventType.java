package org.woehlke.tools.model.config;

import static org.woehlke.tools.model.config.JobCase.*;

public enum JobEventType {

    LOGUBUCH_EVENT("Logbuch Event",ALL),
    SET_ROOT_DIRECTORY( "set Root Directory",ALL),
    TRAVERSE_DIRS( "traverse Directories",ALL),
    TRAVERSE_FILES( "traverse Files",ALL),
    FYI( "fyi",ALL),

    RENAME_DIRECTORIES("rename Directories", JOB_RENAME_DIRECTORIES),
    RENAME_ONE_DIRECTORY( "rename One Directory", JOB_RENAME_DIRECTORIES),

    RENAME_FILES ("rename Files", JOB_RENAME_FILES),
    RENAME_ONE_FILE ("rename One File", JOB_RENAME_FILES),

    SCALE_JPG_IMAGES ("scale jpg images", JOB_IMAGES_RESIZE),
    SCALE_ONE_JPG_IMAGE( "scale one image", JOB_IMAGES_RESIZE);

    JobEventType(String humanReadable, JobCase jobCase){
        this.humanReadable = humanReadable;
        this.jobCase = jobCase;
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
