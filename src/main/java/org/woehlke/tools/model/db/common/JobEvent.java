package org.woehlke.tools.model.db.common;

import org.woehlke.tools.model.db.entities.Job;
import org.woehlke.tools.config.db.JobEventSignal;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Table(name="TOOLS_JOB_EVENT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "JOB_EVENT_TYPE")
public abstract class JobEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private LocalDateTime timestamp;

    @Column
    private UUID uuid;

    @ManyToOne(cascade = {MERGE, REFRESH}, fetch = FetchType.LAZY)
    private Job job;

    @Column
    private JobEventSignal jobEventSignal;

    @Column
    private String eventCode;

    @Column
    private String eventHumanReadable;

    protected JobEvent(JobEventSignal jobEventSignal,
                       Job myJob) {
        uuid = UUID.randomUUID();
        timestamp = LocalDateTime.now();
        setJob(myJob);
        setJobEventSignal(jobEventSignal);
    }

    public JobEvent() {
        uuid = UUID.randomUUID();
        timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventHumanReadable() {
        return eventHumanReadable;
    }

    public void setEventHumanReadable(String eventHumanReadable) {
        this.eventHumanReadable = eventHumanReadable;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public JobEventSignal getJobEventSignal() {
        return jobEventSignal;
    }

    public void setJobEventSignal(JobEventSignal jobEventSignal) {
        this.jobEventSignal = jobEventSignal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobEvent)) return false;
        JobEvent jobEvent = (JobEvent) o;
        return Objects.equals(getId(), jobEvent.getId()) &&
            getTimestamp().equals(jobEvent.getTimestamp()) &&
            getUuid().equals(jobEvent.getUuid()) &&
            getJob().equals(jobEvent.getJob()) &&
            getJobEventSignal() == jobEvent.getJobEventSignal() &&
            getEventCode().equals(jobEvent.getEventCode()) &&
            getEventHumanReadable().equals(jobEvent.getEventHumanReadable());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTimestamp(), getUuid(), getJob(), getJobEventSignal(), getEventCode(), getEventHumanReadable());
    }

    @Override
    public String toString() {
        return "JobEvent{" +
            "id=" + id +
            ", timestamp=" + timestamp +
            ", uuid=" + uuid +
            ", job=" + job +
            ", jobStepSignal=" + jobEventSignal +
            ", eventCode='" + eventCode + '\'' +
            ", eventHumanReadable='" + eventHumanReadable + '\'' +
            '}';
    }
}


