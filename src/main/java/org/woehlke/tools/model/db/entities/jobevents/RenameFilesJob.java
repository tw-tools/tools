package org.woehlke.tools.model.db.entities.jobevents;

import org.woehlke.tools.model.db.entities.Job;
import org.woehlke.tools.model.db.entities.JobEvent;
import org.woehlke.tools.config.db.JobRenameEvent;
import org.woehlke.tools.model.jobs.common.JobEventMessages;
import org.woehlke.tools.config.db.JobEventSignal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

import static org.woehlke.tools.model.db.entities.parts.JobEventDiscriminatorValue.RENAME_FILES_JOB;

@Entity
@DiscriminatorValue(RENAME_FILES_JOB)
public class RenameFilesJob extends JobEvent {

    @Column
    private JobRenameEvent jobRenameEvent;

    public RenameFilesJob(){
        super();
    }

    public RenameFilesJob(
        JobEventSignal jobEventSignal,
        JobRenameEvent jobRenameEvent,
        Job myJob,
        JobEventMessages msg
    ){
        super(jobEventSignal, myJob);
        setJobRenameEvent(jobRenameEvent);
        setEventHumanReadable(msg.get(jobEventSignal, jobRenameEvent));
        setEventCode(msg.getKey(jobEventSignal, jobRenameEvent));
    }


    public JobRenameEvent getJobRenameEvent() {
        return jobRenameEvent;
    }

    public void setJobRenameEvent(JobRenameEvent jobRenameEvent) {
        this.jobRenameEvent = jobRenameEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RenameFilesJob)) return false;
        if (!super.equals(o)) return false;
        RenameFilesJob that = (RenameFilesJob) o;
        return getJobRenameEvent() == that.getJobRenameEvent();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getJobRenameEvent());
    }

    @Override
    public String toString() {
        return "JobEventRenameFilesJob{" +
            "jobRenameEvent=" + jobRenameEvent +
            "} " + super.toString();
    }
}
