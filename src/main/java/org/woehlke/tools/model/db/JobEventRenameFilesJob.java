package org.woehlke.tools.model.db;

import org.woehlke.tools.model.db.common.JobEvent;
import org.woehlke.tools.model.jobs.rename.common.JobRenameEvent;
import org.woehlke.tools.model.jobs.common.JobEventMessages;
import org.woehlke.tools.model.db.config.JobEventSignal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@DiscriminatorValue("RenameFilesJob")
public class JobEventRenameFilesJob extends JobEvent {

    @Column
    private JobRenameEvent jobRenameEvent;

    public JobEventRenameFilesJob(){
        super();
    }

    public JobEventRenameFilesJob(
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
        if (!(o instanceof JobEventRenameFilesJob)) return false;
        if (!super.equals(o)) return false;
        JobEventRenameFilesJob that = (JobEventRenameFilesJob) o;
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
