package org.woehlke.tools.model.entities;

import org.woehlke.tools.model.config.JobEventType;
import org.woehlke.tools.model.config.JobEventSignal;
import org.woehlke.tools.model.common.JobEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.Serializable;
import java.util.Objects;

import static org.woehlke.tools.model.common.JobEventDiscriminatorValue.SCALED_IMAGE_JPG;

@Entity
@DiscriminatorValue(SCALED_IMAGE_JPG)
public class ScaledImageJpg extends JobEvent implements Serializable {

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

    @NotNull
    @Column
    private Long lengthScaled;

    @NotNull
    @Column
    private Long widthScaled;

    @NotBlank
    @Column(length=65000, columnDefinition = "TEXT")
    private String command;

    @NotBlank
    @Column
    private String resultMessage;

    /*
    @NotNull
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="filename", column=@Column(name="src_filename")),
        @AttributeOverride(name="filepath", column=@Column(name="src_filepath",length=65000, columnDefinition = "TEXT")),
        @AttributeOverride(name="length", column=@Column(name="src_length")),
        @AttributeOverride(name="width", column=@Column(name="src_width")),
    })
    private JobEventScaledImageJpgFile source;

    @NotNull
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="filename", column=@Column(name="target_filename")),
        @AttributeOverride(name="filepath", column=@Column(name="target_filepath",length=65000, columnDefinition = "TEXT")),
        @AttributeOverride(name="length", column=@Column(name="target_length")),
        @AttributeOverride(name="width", column=@Column(name="target_width")),
    })
    private JobEventScaledImageJpgFile target;
    */

    public ScaledImageJpg() {
       super();
    }

    public ScaledImageJpg(
        File jpgFile,
        long length,
        long width,
        JobEventSignal jobEventSignal,
        JobEventType jobEventType,
        Job myJob
    ) {
        super(myJob, jobEventType, jobEventSignal);
        this.filename = jpgFile.getName();
        this.filepath=jpgFile.getAbsolutePath();
        this.length = length;
        this.width = width;
    }

    @Transient
    public int getScaleFactorAsPercent(  int targetScale){
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

    @Transient
    public boolean querFormat(){
        return this.width > this.length;
    }

    @Transient
    public String getCategory() {
        return "UDEFINED_CATEGORY";
    }

    @Transient
    public String getLine() {
        return "UNDEFINED_LINE";
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

    public Long getLengthScaled() {
        return lengthScaled;
    }

    public void setLengthScaled(Long lengthScaled) {
        this.lengthScaled = lengthScaled;
    }

    public Long getWidthScaled() {
        return widthScaled;
    }

    public void setWidthScaled(Long widthScaled) {
        this.widthScaled = widthScaled;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScaledImageJpg)) return false;
        if (!super.equals(o)) return false;
        ScaledImageJpg that = (ScaledImageJpg) o;
        return getFilename().equals(that.getFilename()) &&
            getFilepath().equals(that.getFilepath()) &&
            getLength().equals(that.getLength()) &&
            getWidth().equals(that.getWidth()) &&
            Objects.equals(getLengthScaled(), that.getLengthScaled()) &&
            Objects.equals(getWidthScaled(), that.getWidthScaled()) &&
            Objects.equals(getCommand(), that.getCommand()) &&
            Objects.equals(getResultMessage(), that.getResultMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFilename(), getFilepath(), getLength(), getWidth(), getLengthScaled(), getWidthScaled(), getCommand(), getResultMessage());
    }
}
