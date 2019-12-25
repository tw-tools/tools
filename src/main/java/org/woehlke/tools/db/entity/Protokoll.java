package org.woehlke.tools.db.entity;


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
    private String category;

    @Column
    private String job;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Protokoll)) return false;
        Protokoll protokoll = (Protokoll) o;
        return Objects.equals(getId(), protokoll.getId()) &&
            getLine().equals(protokoll.getLine()) &&
            Objects.equals(getCategory(), protokoll.getCategory()) &&
            Objects.equals(getJob(), protokoll.getJob()) &&
            getUuid().equals(protokoll.getUuid()) &&
            getTimestamp().equals(protokoll.getTimestamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLine(), getCategory(), getJob(), getUuid(), getTimestamp());
    }
}
