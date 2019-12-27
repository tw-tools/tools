package org.woehlke.tools.db;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name="TOOLS_RENAMED")
public class Renamed implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String parent;

    @Column
    private String source;

    @Column
    private String target;

    @Column
    private boolean directory;

    @Column
    private boolean dryRun;

    @Column
    private UUID uuid;

    @ManyToOne(cascade={ MERGE, REFRESH},fetch = FetchType.LAZY)
    private Job job;

    @Column
    private LocalDateTime timestamp;

    public Renamed() {
        uuid = UUID.randomUUID();
        timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public boolean isDryRun() {
        return dryRun;
    }

    public void setDryRun(boolean dryRun) {
        this.dryRun = dryRun;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Renamed)) return false;
        Renamed renamed = (Renamed) o;
        return isDirectory() == renamed.isDirectory() &&
            isDryRun() == renamed.isDryRun() &&
            Objects.equals(getId(), renamed.getId()) &&
            getParent().equals(renamed.getParent()) &&
            getSource().equals(renamed.getSource()) &&
            getTarget().equals(renamed.getTarget()) &&
            getUuid().equals(renamed.getUuid()) &&
            getJob().equals(renamed.getJob()) &&
            getTimestamp().equals(renamed.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getParent(), getSource(), getTarget(), isDirectory(), isDryRun(), getUuid(), getJob(), getTimestamp());
    }

    @Override
    public String toString() {
        return "Renamed{" +
            "id=" + id +
            ", parent='" + parent + '\'' +
            ", source='" + source + '\'' +
            ", target='" + target + '\'' +
            ", directory=" + directory +
            ", dryRun=" + dryRun +
            ", uuid=" + uuid +
            ", job=" + job.getId() +
            ", timestamp=" + timestamp +
            '}';
    }
}
