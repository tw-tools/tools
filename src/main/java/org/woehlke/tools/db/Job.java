package org.woehlke.tools.db;

import org.woehlke.tools.db.common.JobCase;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private LocalDateTime started;

    @Column
    private LocalDateTime finished;

    @Column
    private String rootDirectory;

    @Column
    private boolean dryRun;

    @Column
    private boolean dbActive;

    @Column
    private JobCase jobCase;

    @Column
    private UUID uuid;

    public Job() {
        uuid = UUID.randomUUID();
        started = LocalDateTime.now();
    }

    public static Job create(JobCase jobCase, String dataRootDir, boolean dryRun, boolean dbActive){
        Job job = new Job();
        job.setJobCase(jobCase);
        job.setRootDirectory(dataRootDir);
        job.setDryRun(dryRun);
        job.setDbActive(dbActive);
        return job;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStarted() {
        return started;
    }

    public void setStarted(LocalDateTime started) {
        this.started = started;
    }

    public LocalDateTime getFinished() {
        return finished;
    }

    public void setFinished(LocalDateTime finished) {
        this.finished = finished;
    }

    public String getRootDirectory() {
        return rootDirectory;
    }

    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public boolean isDryRun() {
        return dryRun;
    }

    public void setDryRun(boolean dryRun) {
        this.dryRun = dryRun;
    }

    public JobCase getJobCase() {
        return jobCase;
    }

    public void setJobCase(JobCase jobCase) {
        this.jobCase = jobCase;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isDbActive() {
        return dbActive;
    }

    public void setDbActive(boolean dbActive) {
        this.dbActive = dbActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Job)) return false;
        Job job = (Job) o;
        return isDryRun() == job.isDryRun() &&
            isDbActive() == job.isDbActive() &&
            Objects.equals(getId(), job.getId()) &&
            getStarted().equals(job.getStarted()) &&
            Objects.equals(getFinished(), job.getFinished()) &&
            getRootDirectory().equals(job.getRootDirectory()) &&
            getJobCase() == job.getJobCase() &&
            getUuid().equals(job.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStarted(), getFinished(), getRootDirectory(), isDryRun(), isDbActive(), getJobCase(), getUuid());
    }
}
