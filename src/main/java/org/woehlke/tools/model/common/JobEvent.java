package org.woehlke.tools.model.common;

import org.woehlke.tools.model.config.JobEventSignal;
import org.woehlke.tools.model.config.JobEventType;
import org.woehlke.tools.model.entities.Job;

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
@DiscriminatorColumn(name = "JOB_EVENT_DISCRIMINATOR")
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
    private JobEventType jobEventType;

    @Column
    private JobEventSignal jobEventSignal;

    protected JobEvent(
        Job myJob,
        JobEventType jobEventType,
        JobEventSignal jobEventSignal
    ) {
        setUuid(UUID.randomUUID());
        setTimestamp(LocalDateTime.now());
        setJobEventType(jobEventType);
        setJob(myJob);
        setJobEventSignal(jobEventSignal);
    }

    public JobEvent() {
        setUuid(UUID.randomUUID());
        setTimestamp(LocalDateTime.now());
    }

    @Transient
    public String getJobEventMessage(){
        return jobEventSignal +" "+jobEventType+" @ "+jobEventType.getJobCase()+" "+ timestamp.toString();
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

    public JobEventType getJobEventType() {
        return jobEventType;
    }

    public void setJobEventType(JobEventType jobEventType) {
        this.jobEventType = jobEventType;
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
            getJobEventType() == jobEvent.getJobEventType() &&
            getJobEventSignal() == jobEvent.getJobEventSignal();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTimestamp(), getUuid(), getJob(), getJobEventType(), getJobEventSignal());
    }

}


