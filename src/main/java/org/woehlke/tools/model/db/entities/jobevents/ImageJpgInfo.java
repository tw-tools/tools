package org.woehlke.tools.model.db.entities.jobevents;

import org.woehlke.tools.model.db.entities.Job;
import org.woehlke.tools.model.db.entities.JobEvent;
import org.woehlke.tools.config.db.JobEventSignal;
import org.woehlke.tools.model.db.entities.parts.JobEventScaledImageJpgFile;
import org.woehlke.tools.model.jobs.common.JobEventMessages;
import org.woehlke.tools.config.db.JobScaleImagesEvent;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static org.woehlke.tools.model.db.entities.parts.JobEventDiscriminatorValue.IMAGE_JPG_INFO;

@Entity
@DiscriminatorValue(IMAGE_JPG_INFO)
public class ImageJpgInfo extends JobEvent {

    @NotNull
    @Embedded
    private JobEventScaledImageJpgFile source;

    public ImageJpgInfo() {
        super();
    }

    public ImageJpgInfo(
        JobEventScaledImageJpgFile source,
        JobEventSignal jobEventSignal,
        Job myJob,
        JobScaleImagesEvent jobRenameEvent,
        JobEventMessages msg
    ) {
        super(jobEventSignal, myJob);
        this.source = source;
        setEventHumanReadable(msg.get(jobEventSignal, jobRenameEvent));
        setEventCode(msg.getKey(jobEventSignal, jobRenameEvent));
    }

    public JobEventScaledImageJpgFile getSource() {
        return source;
    }

    public void setSource(JobEventScaledImageJpgFile source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageJpgInfo)) return false;
        if (!super.equals(o)) return false;
        ImageJpgInfo that = (ImageJpgInfo) o;
        return getSource().equals(that.getSource());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getSource());
    }

    @Override
    public String toString() {
        return "JobEventImageJpgInfo{" +
            "source=" + source +
            "} " + super.toString();
    }
}
