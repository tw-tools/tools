package org.woehlke.tools.model.db;

import org.woehlke.tools.model.db.common.JobEvent;
import org.woehlke.tools.jobs.images.common.JobScaleImagesEvent;
import org.woehlke.tools.jobs.common.JobEventMessages;
import org.woehlke.tools.model.db.config.JobEventSignal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@DiscriminatorValue("ScaleImagesJob")
public class JobEventScaleImagesJob extends JobEvent {

    @Column
    private JobScaleImagesEvent jobScaleImagesEvent;

    public JobEventScaleImagesJob() {
        super();
    }

    public JobEventScaleImagesJob(JobEventSignal jobEventSignal,
                                  JobScaleImagesEvent jobScaleImagesEvent,
                                  Job myJob,
                                  JobEventMessages msg) {
        super(jobEventSignal, myJob);
        setJobScaleImagesEvent(jobScaleImagesEvent);
        setEventHumanReadable(msg.get(jobEventSignal, jobScaleImagesEvent));
        setEventCode(msg.getKey(jobEventSignal, jobScaleImagesEvent));
    }

    public JobScaleImagesEvent getJobScaleImagesEvent() {
        return jobScaleImagesEvent;
    }

    public void setJobScaleImagesEvent(JobScaleImagesEvent jobScaleImagesEvent) {
        this.jobScaleImagesEvent = jobScaleImagesEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobEventScaleImagesJob)) return false;
        if (!super.equals(o)) return false;
        JobEventScaleImagesJob that = (JobEventScaleImagesJob) o;
        return getJobScaleImagesEvent() == that.getJobScaleImagesEvent();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getJobScaleImagesEvent());
    }

    @Override
    public String toString() {
        return "JobEventScaleImagesJob{" +
            "jobScaleImagesEvent=" + jobScaleImagesEvent +
            "} " + super.toString();
    }
}
