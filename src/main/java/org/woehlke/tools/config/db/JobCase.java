package org.woehlke.tools.config.db;

public enum JobCase {

    JOB_RENAME_DIRECTORIES("Rename Directories"),
    JOB_RENAME_FILES("Rename Files"),
    JOB_SCALE_IMAGES("Scale Images"),

    ALL("");

    JobCase(String humanReadable){
        this.humanReadable = "Job "+humanReadable;
    }

    private String humanReadable;

    public String getHumanReadable() {
        return humanReadable;
    }
}
