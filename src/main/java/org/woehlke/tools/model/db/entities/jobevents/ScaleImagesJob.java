package org.woehlke.tools.model.db.entities.jobevents;

import org.woehlke.tools.model.db.entities.Job;
import org.woehlke.tools.model.db.entities.JobEvent;
import org.woehlke.tools.config.db.JobScaleImagesEvent;
import org.woehlke.tools.model.jobs.common.JobEventMessages;
import org.woehlke.tools.config.db.JobEventSignal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

import static org.woehlke.tools.model.db.entities.parts.JobEventDiscriminatorValue.SCALE_IMAGES_JOB;




@Entity
@DiscriminatorValue(SCALE_IMAGES_JOB)
public class ScaleImagesJob extends JobEvent {

    @Column
    private JobScaleImagesEvent jobScaleImagesEvent;

    public ScaleImagesJob() {
        super();
    }

    public ScaleImagesJob(JobEventSignal jobEventSignal,
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
        if (!(o instanceof ScaleImagesJob)) return false;
        if (!super.equals(o)) return false;
        ScaleImagesJob that = (ScaleImagesJob) o;
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
