package org.woehlke.tools.db.entity;


import org.hibernate.id.UUIDGenerator;
import org.hibernate.type.EntityType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Protokoll implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String line;

    @Column
    private UUID uuid;

    @Column
    private LocalDateTime timestamp;

    public Protokoll() {
    }

    public Protokoll(String line) {
        this.line = line;
        uuid = UUID.randomUUID();
        timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Protokoll)) return false;
        Protokoll protokoll = (Protokoll) o;
        return Objects.equals(getId(), protokoll.getId()) &&
            getLine().equals(protokoll.getLine()) &&
            getUuid().equals(protokoll.getUuid()) &&
            getTimestamp().equals(protokoll.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLine(), getUuid(), getTimestamp());
    }

    @Override
    public String toString() {
        return "Protokoll{" +
            "id=" + id +
            ", line='" + line + '\'' +
            ", uuid=" + uuid +
            ", timestamp=" + timestamp +
            '}';
    }
}
