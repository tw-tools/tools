package org.woehlke.tools.model.db.entities.jobevents;

import org.woehlke.tools.model.db.entities.Job;
import org.woehlke.tools.model.db.entities.JobEvent;
import org.woehlke.tools.config.db.JobEventSignal;
import org.woehlke.tools.model.db.entities.parts.JobEventScaledImageJpgFile;
import org.woehlke.tools.model.jobs.common.JobEventMessages;
import org.woehlke.tools.config.db.JobScaleImagesEvent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static org.woehlke.tools.model.db.entities.parts.JobEventDiscriminatorValue.SCALED_IMAGE_JPG;

@Entity
@DiscriminatorValue(SCALED_IMAGE_JPG)
public class ScaledImageJpg extends JobEvent {

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

    public ScaledImageJpg() {
       super();
    }

    public ScaledImageJpg(
        JobEventScaledImageJpgFile source,
        JobEventScaledImageJpgFile target,
        JobEventSignal jobEventSignal,
        JobScaleImagesEvent jobRenameEvent,
        Job myJob,
        JobEventMessages msg
    ) {
        super(jobEventSignal, myJob);
        this.source = source;
        this.target = target;
        setEventHumanReadable(msg.get(jobEventSignal, jobRenameEvent));
        setEventCode(msg.getKey(jobEventSignal, jobRenameEvent));
    }

    public JobEventScaledImageJpgFile getSource() {
        return source;
    }

    public void setSource(JobEventScaledImageJpgFile source) {
        this.source = source;
    }

    public JobEventScaledImageJpgFile getTarget() {
        return target;
    }

    public void setTarget(JobEventScaledImageJpgFile target) {
        this.target = target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScaledImageJpg)) return false;
        if (!super.equals(o)) return false;
        ScaledImageJpg scaledImageJpg = (ScaledImageJpg) o;
        return getSource().equals(scaledImageJpg.getSource()) &&
            getTarget().equals(scaledImageJpg.getTarget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSource(), getTarget());
    }

    @Override
    public String toString() {
        return "ImageJpg{" +
            "source=" + source +
            ", target=" + target +
            "} " + super.toString();
    }
}
