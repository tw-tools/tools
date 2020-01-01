package org.woehlke.tools.model.config;

import static org.woehlke.tools.config.properties.QueueNames.*;

public enum JobCase {

    JOB_RENAME_DIRECTORIES("Rename Directories",JOB_RENAME_QUEUE),
    JOB_RENAME_FILES("Rename Files", JOB_RENAME_QUEUE),
    JOB_IMAGES_RESIZE("Scale Images", JOB_IMAGES_RESIZE_QUEUE),
    JOB_IMAGES_INFO_JPG("Info JPG Images", JOB_IMAGES_INFO_QUEUE),
    JOB_IMAGES_INFO_PNG("Info PNG Images", JOB_IMAGES_INFO_QUEUE),

    ALL("all, undefined or default", LOGBUCH_QUEUE);

    JobCase(String humanReadable, String queue){
        this.humanReadable = humanReadable;
        this.queue=queue;
    }

    private String humanReadable;

    public String getHumanReadable() {
        return humanReadable;
    }

    private String queue;

    public String getQueue() {
        return queue;
    }

    public String getQueueReply() {
        return queue+REPLY;
    }
}
