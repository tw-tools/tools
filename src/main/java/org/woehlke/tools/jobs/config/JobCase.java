package org.woehlke.tools.jobs.config;

public enum JobCase {

    JOB_RENAME_DIRECTORIES("Rename Directories"),
    JOB_RENAME_FILES("Rename Files"),
    JOB_SCALE_IMAGES("Scale Images"),
    JOB_IMAGES_INFO_JPG("Info JPG Images"),
    JOB_IMAGES_INFO_PNG("Info PNG Images"),

    ALL("");

    JobCase(String humanReadable){
        this.humanReadable = "Job "+humanReadable;
    }

    private String humanReadable;

    public String getHumanReadable() {
        return humanReadable;
    }
}
