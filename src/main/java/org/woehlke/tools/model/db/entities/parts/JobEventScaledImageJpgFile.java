package org.woehlke.tools.model.db.entities.parts;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.Objects;

@Embeddable
public class JobEventScaledImageJpgFile {

    @NotBlank
    @Column
    private String filename;

    @NotBlank
    @Column(length=65000,columnDefinition = "TEXT")
    private String filepath;

    @NotNull
    @Column
    private Long length;

    @NotNull
    @Column
    private Long width;

    public JobEventScaledImageJpgFile() {
    }

    public JobEventScaledImageJpgFile(File jpgFile, long length, long width) {
        this.filename = jpgFile.getName();
        this.filepath = jpgFile.getAbsolutePath();
        this.length = length;
        this.width = width;
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

    public boolean querFormat(){
        return this.width > this.length;
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
        if (!(o instanceof JobEventScaledImageJpgFile)) return false;
        JobEventScaledImageJpgFile that = (JobEventScaledImageJpgFile) o;
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
        return "ImageJpgFile{" +
            "filename='" + filename + '\'' +
            ", filepath='" + filepath + '\'' +
            ", length=" + length +
            ", width=" + width +
            '}';
    }
}
