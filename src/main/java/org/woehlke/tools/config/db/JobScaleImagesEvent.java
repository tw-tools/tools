package org.woehlke.tools.config.db;

public enum JobScaleImagesEvent {

    SCALE_JPG_IMAGES ("scale jpg images"),
    SCALE_IMAGE( "scale one image"),
    TRAVERSE_DIRS( "traverse Dirs"),
    TRAVERSE_FILES( "traverse Files"),
    SET_ROOT_DIRECTORY( "set Root Directory");

    JobScaleImagesEvent(String humanReadable){
        this.humanReadable = humanReadable;
    }

    private String humanReadable;

    public String getHumanReadable() {
        return humanReadable;
    }
}
