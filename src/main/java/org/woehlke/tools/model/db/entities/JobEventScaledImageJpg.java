package org.woehlke.tools.model.db.entities;

import org.woehlke.tools.model.db.common.JobEvent;
import org.woehlke.tools.config.db.JobEventSignal;
import org.woehlke.tools.model.jobs.common.JobEventMessages;
import org.woehlke.tools.config.db.JobScaleImagesEvent;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@DiscriminatorValue("ScaledImageJpg")
public class JobEventScaledImageJpg extends JobEvent {

    @NotNull
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="filename", column=@Column(name="src_filename")),
        @AttributeOverride(name="filepath", column=@Column(name="src_filepath")),
        @AttributeOverride(name="length", column=@Column(name="src_length")),
        @AttributeOverride(name="width", column=@Column(name="src_width")),
    })
    private JobEventScaledImageJpgFile source;

    @NotNull
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="filename", column=@Column(name="target_filename")),
        @AttributeOverride(name="filepath", column=@Column(name="target_filepath")),
        @AttributeOverride(name="length", column=@Column(name="target_length")),
        @AttributeOverride(name="width", column=@Column(name="target_width")),
    })
    private JobEventScaledImageJpgFile target;

    public JobEventScaledImageJpg() {
       super();
    }

    public JobEventScaledImageJpg(
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
        if (!(o instanceof JobEventScaledImageJpg)) return false;
        if (!super.equals(o)) return false;
        JobEventScaledImageJpg jobEventScaledImageJpg = (JobEventScaledImageJpg) o;
        return getSource().equals(jobEventScaledImageJpg.getSource()) &&
            getTarget().equals(jobEventScaledImageJpg.getTarget());
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
