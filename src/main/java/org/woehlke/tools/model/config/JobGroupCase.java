package org.woehlke.tools.model.config;

import static org.woehlke.tools.config.properties.QueueNames.*;

public enum JobGroupCase {

    JOB_RENAME("Rename Directories and Files", JOB_RENAME_QUEUE),
    JOB_IMAGES_RESIZE("Resize Images", JOB_IMAGES_RESIZE_QUEUE),
    JOB_IMAGES_INFO("Collect Infos about Images", JOB_IMAGES_INFO_QUEUE);

    JobGroupCase(String humanReadable, String queue){
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
