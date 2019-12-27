package org.woehlke.tools.db;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


import static javax.persistence.CascadeType.*;

@Entity
@Table(name="TOOLS_IMAGE_JPG")
public class ImageJpg implements Serializable {

    private static final long serialVersionUID = 8227369680253280265L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String filename;

    @Column(length=4096)
    private String filepath;

    @Column
    private long length;

    @Column
    private long width;

    @Column
    private UUID uuid;

    @Column
    private LocalDateTime timestamp;

    @ManyToOne(cascade={ MERGE, REFRESH },fetch = FetchType.LAZY)
    private Job job;

    public ImageJpg() {
        uuid = UUID.randomUUID();
        timestamp = LocalDateTime.now();
    }

    public static ImageJpg create(File jpgFile, long length, long width) {
        ImageJpg img = new ImageJpg();
        img.setFilename(jpgFile.getName());
        img.setFilepath(jpgFile.getParent());
        img.setLength(length);
        img.setWidth(width);
        return img;
    }

    public int scaleFactor(){
        Long hundred = 100L;
        Long x = 1600L;
        Long d;
        if(querFormat()){
            d = Long.divideUnsigned(( hundred * x ), this.width);
        } else {
            d = Long.divideUnsigned(( hundred * x ), this.length);
        }
        return d.intValue();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean querFormat(){
        return this.width > this.length;
    }

    public long getLength() {
        return length;
    }

    public long getWidth() {
        return width;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public void setWidth(long width) {
        this.width = width;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageJpg)) return false;
        ImageJpg imageJpg = (ImageJpg) o;
        return getLength() == imageJpg.getLength() &&
            getWidth() == imageJpg.getWidth() &&
            Objects.equals(getId(), imageJpg.getId()) &&
            getFilename().equals(imageJpg.getFilename()) &&
            getFilepath().equals(imageJpg.getFilepath()) &&
            getUuid().equals(imageJpg.getUuid()) &&
            getTimestamp().equals(imageJpg.getTimestamp()) &&
            getJob().equals(imageJpg.getJob());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFilename(), getFilepath(), getLength(), getWidth(), getUuid(), getTimestamp(), getJob());
    }

    @Override
    public String toString() {
        return "ImageJpg{" +
            "id=" + id +
            ", filename='" + filename + '\'' +
            ", filepath='" + filepath + '\'' +
            ", length=" + length +
            ", width=" + width +
            ", uuid=" + uuid +
            ", timestamp=" + timestamp +
            ", job=" + job.getJobCase().toString() +
            '}';
    }
}
