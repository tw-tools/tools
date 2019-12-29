package org.woehlke.tools.jobs.impl;

import org.woehlke.tools.db.common.JobCase;

public enum JobScaleImagesStep {

    SCALE_JPG_IMAGES ("rename Files"),
    SCALE_IMAGE( "scale one image"),
    TRAVERSE_DIRS( "traverse Dirs"),
    TRAVERSE_FILES( "traverse Files"),
    SET_ROOT_DIRECTORY( "setRootDirectory");

    JobScaleImagesStep(String humanReadable){
        this.humanReadable = humanReadable;
    }

    private String humanReadable;

    public String getHumanReadable() {
        return humanReadable;
    }
}
