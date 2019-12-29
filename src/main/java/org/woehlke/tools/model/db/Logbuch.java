package org.woehlke.tools.model.db;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.CascadeType.*;

@Deprecated
@Entity
@Table(name="TOOLS_LOGBUCH")
public class Logbuch implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String line;

    @Column
    private String category;

    @Column
    private UUID uuid;

    @Column
    private LocalDateTime timestamp;

    @ManyToOne(cascade={ MERGE, REFRESH},fetch = FetchType.LAZY)
    private Job job;

    @Deprecated
    public Logbuch() {
        uuid = UUID.randomUUID();
        timestamp = LocalDateTime.now();
    }

    @Deprecated
    public Logbuch(String line) {
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
        if(line.length()>255){
            this.line = line.substring(0,255);
        } else {
            this.line = line;
        }
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
        if((category != null) && (category.length() > 255)) {
            this.category = category.substring(0, 255);
        } else {
            this.category = category;
        }
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Logbuch)) return false;
        Logbuch logbuch = (Logbuch) o;
        return Objects.equals(getId(), logbuch.getId()) &&
            getLine().equals(logbuch.getLine()) &&
            getCategory().equals(logbuch.getCategory()) &&
            getUuid().equals(logbuch.getUuid()) &&
            getTimestamp().equals(logbuch.getTimestamp()) &&
            getJob().equals(logbuch.getJob());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLine(), getCategory(), getUuid(), getTimestamp(), getJob());
    }
}
