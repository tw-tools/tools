package org.woehlke.tools.db;

import org.woehlke.tools.jobs.impl.JobScaleImagesStep;
import org.woehlke.tools.jobs.impl.JobStepMessages;
import org.woehlke.tools.jobs.impl.JobRenameStep;
import org.woehlke.tools.jobs.impl.JobStepSignal;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Table(name="TOOLS_JOB_EVENT")
public class JobStep implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private LocalDateTime timestamp;

    @Column
    private UUID uuid;

    @ManyToOne(cascade={ MERGE, REFRESH }, fetch = FetchType.LAZY)
    private Job job;

    @Column
    private JobStepSignal jobStepSignal;

    @Column
    private JobRenameStep jobRenameStep;

    @Column
    private  JobScaleImagesStep jobScaleImagesStep;

    @Column
    private String eventCode;

    @Column
    private String eventHumanReadable;


    public static JobStep create(JobStepSignal jobStepSignal,
                                 JobRenameStep jobRenameStep,
                                 Job myJob,
                                 JobStepMessages msg){
        JobStep step = JobStep.create(jobStepSignal, myJob);
        step.setJobRenameStep(jobRenameStep);
        step.setEventHumanReadable(msg.get(jobStepSignal,jobRenameStep));
        step.setEventCode(msg.getKey(jobStepSignal,jobRenameStep));
        return step;
    }

    public static JobStep create(JobStepSignal jobStepSignal,
                                 Job myJob){
        JobStep step = new JobStep();
        step.setJob(myJob);
        step.setJobStepSignal(jobStepSignal);
        return step;
    }

    public static JobStep create(JobStepSignal jobStepSignal,
                                 JobScaleImagesStep jobScaleImagesStep,
                                 Job myJob,
                                 JobStepMessages msg){
        JobStep step = JobStep.create(jobStepSignal, myJob);
        step.setJobScaleImagesStep(jobScaleImagesStep);
        step.setEventHumanReadable(msg.get(jobStepSignal,jobScaleImagesStep));
        step.setEventCode(msg.getKey(jobStepSignal,jobScaleImagesStep));
        return step;
    }

    public JobStep() {
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

    public JobRenameStep getJobRenameStep() {
        return jobRenameStep;
    }

    public void setJobRenameStep(JobRenameStep jobRenameStep) {
        this.jobRenameStep = jobRenameStep;
    }

    public JobStepSignal getJobStepSignal() {
        return jobStepSignal;
    }

    public void setJobStepSignal(JobStepSignal jobStepSignal) {
        this.jobStepSignal = jobStepSignal;
    }

    public JobScaleImagesStep getJobScaleImagesStep() {
        return jobScaleImagesStep;
    }

    public void setJobScaleImagesStep(JobScaleImagesStep jobScaleImagesStep) {
        this.jobScaleImagesStep = jobScaleImagesStep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobStep)) return false;
        JobStep jobStep = (JobStep) o;
        return Objects.equals(getId(), jobStep.getId()) &&
            getJob().equals(jobStep.getJob()) &&
            getTimestamp().equals(jobStep.getTimestamp()) &&
            getEventCode().equals(jobStep.getEventCode()) &&
            getEventHumanReadable().equals(jobStep.getEventHumanReadable()) &&
            getUuid().equals(jobStep.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getJob(), getTimestamp(), getEventCode(), getEventHumanReadable(), getUuid());
    }
}
