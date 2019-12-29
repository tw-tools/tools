package org.woehlke.tools.model.db.entities;

import org.woehlke.tools.config.db.JobCase;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="TOOLS_JOB")
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column
    private LocalDateTime started;

    @Column
    private LocalDateTime finished;

    @NotBlank
    @Column
    private String rootDirectory;

    @NotNull
    @Column
    private Boolean dryRun;

    @NotNull
    @Column
    private Boolean dbActive;

    @NotNull
    @Column
    private JobCase jobCase;

    @NotNull
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

    public Boolean getDryRun() {
        return dryRun;
    }

    public void setDryRun(Boolean dryRun) {
        this.dryRun = dryRun;
    }

    public Boolean getDbActive() {
        return dbActive;
    }

    public void setDbActive(Boolean dbActive) {
        this.dbActive = dbActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Job)) return false;
        Job job = (Job) o;
        return Objects.equals(getId(), job.getId()) &&
            getStarted().equals(job.getStarted()) &&
            Objects.equals(getFinished(), job.getFinished()) &&
            getRootDirectory().equals(job.getRootDirectory()) &&
            getDryRun().equals(job.getDryRun()) &&
            getDbActive().equals(job.getDbActive()) &&
            getJobCase() == job.getJobCase() &&
            getUuid().equals(job.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStarted(), getFinished(), getRootDirectory(), getDryRun(), getDbActive(), getJobCase(), getUuid());
    }

    @Override
    public String toString() {
        return "Job{" +
            "id=" + id +
            ", started=" + started +
            ", finished=" + finished +
            ", rootDirectory='" + rootDirectory + '\'' +
            ", dryRun=" + dryRun +
            ", dbActive=" + dbActive +
            ", jobCase=" + jobCase +
            ", uuid=" + uuid +
            '}';
    }
}
