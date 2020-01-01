package org.woehlke.tools.model.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="TOOLS_JOB_GROUP")
public class JobGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column
    private LocalDateTime started;

    @Column
    private LocalDateTime finished;

    @NotBlank
    @Column(length=65000, columnDefinition = "TEXT")
    private String rootDirectory;

    @NotNull
    @Column
    private UUID uuid;

    @NotNull
    @Column
    private Boolean dryRun;

    @NotNull
    @Column
    private Boolean dbActive;

    @NotNull
    @OneToMany
    private Set<Job> jobSet;

    public JobGroup() {
        uuid = UUID.randomUUID();
        started = LocalDateTime.now();
        jobSet = Collections.synchronizedSortedSet(new TreeSet<>());
    }

    public JobGroup(File rootDirectory, boolean dryRun, boolean dbActive) {
        uuid = UUID.randomUUID();
        started = LocalDateTime.now();
        jobSet = Collections.synchronizedSortedSet(new TreeSet<>());
        this.dryRun=dryRun;
        this.dbActive=dbActive;
        this.rootDirectory = rootDirectory.getAbsolutePath();
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

    public Set<Job> getJobSet() {
        return jobSet;
    }

    public void setJobSet(Set<Job> jobSet) {
        this.jobSet = jobSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobGroup)) return false;
        JobGroup jobGroup = (JobGroup) o;
        return Objects.equals(getId(), jobGroup.getId()) &&
            getStarted().equals(jobGroup.getStarted()) &&
            Objects.equals(getFinished(), jobGroup.getFinished()) &&
            getRootDirectory().equals(jobGroup.getRootDirectory()) &&
            getUuid().equals(jobGroup.getUuid()) &&
            getDryRun().equals(jobGroup.getDryRun()) &&
            getDbActive().equals(jobGroup.getDbActive()) &&
            getJobSet().equals(jobGroup.getJobSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStarted(), getFinished(), getRootDirectory(), getUuid(), getDryRun(), getDbActive(), getJobSet());
    }
}
