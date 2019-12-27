package org.woehlke.tools.db;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
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
            getId().equals(renamed.getId()) &&
            Objects.equals(getParent(), renamed.getParent()) &&
            Objects.equals(getSource(), renamed.getSource()) &&
            Objects.equals(getTarget(), renamed.getTarget()) &&
            Objects.equals(getUuid(), renamed.getUuid()) &&
            Objects.equals(getTimestamp(), renamed.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getParent(), getSource(), getTarget(), isDirectory(), isDryRun(), getUuid(), getTimestamp());
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
            ", timestamp=" + timestamp +
            '}';
    }
}
