package org.woehlke.tools.model.db.entities;

import org.woehlke.tools.config.db.JobEventType;
import org.woehlke.tools.config.db.JobEventSignal;
import org.woehlke.tools.model.db.common.JobEvent;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.Objects;

import static org.woehlke.tools.model.db.common.JobEventDiscriminatorValue.IMAGE_JPG_INFO;

@Entity
@DiscriminatorValue(IMAGE_JPG_INFO)
public class ImageJpgInfo extends JobEvent {

    @NotBlank
    @Column
    private String filename;

    @NotBlank
    @Column(length=65000, columnDefinition = "TEXT")
    private String filepath;

    @NotNull
    @Column
    private Long length;

    @NotNull
    @Column
    private Long width;

    public ImageJpgInfo() {
        super();
    }

    public ImageJpgInfo(
        File jpgFile,
        long length,
        long width,
        Job myJob,
        JobEventType jobEventType,
        JobEventSignal jobEventSignal
    ) {
        super(myJob, jobEventType, jobEventSignal);
        this.filename = jpgFile.getName();
        this.filepath = jpgFile.getAbsolutePath();
        this.length = length;
        this.width = width;
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

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageJpgInfo)) return false;
        ImageJpgInfo that = (ImageJpgInfo) o;
        return Objects.equals(getFilename(), that.getFilename()) &&
            Objects.equals(getFilepath(), that.getFilepath()) &&
            Objects.equals(getLength(), that.getLength()) &&
            Objects.equals(getWidth(), that.getWidth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFilename(), getFilepath(), getLength(), getWidth());
    }

    @Override
    public String toString() {
        return "ImageJpgInfo{" +
            "filename='" + filename + '\'' +
            ", filepath='" + filepath + '\'' +
            ", length=" + length +
            ", width=" + width +
            "} " + super.toString();
    }
}
