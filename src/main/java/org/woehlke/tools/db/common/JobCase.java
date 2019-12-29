package org.woehlke.tools.db.common;

public enum JobCase {
    JOB_RENAME_FILES("Rename Files and Dirs"),
    JOB_SCALE_IMAGES("Scale Images");

    JobCase(String humanReadable){
        this.humanReadable = "Job "+humanReadable;
    }

    private String humanReadable;

    public String getHumanReadable() {
        return humanReadable;
    }
}
