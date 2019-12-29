package org.woehlke.tools.model.db;

import org.woehlke.tools.model.db.common.JobEvent;
import org.woehlke.tools.model.db.config.JobEventSignal;
import org.woehlke.tools.model.jobs.common.JobEventMessages;
import org.woehlke.tools.model.jobs.images.common.JobScaleImagesEvent;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@DiscriminatorValue("ImageJpgInfo")
public class JobEventImageJpgInfo extends JobEvent {

    @NotNull
    @Embedded
    private JobEventScaledImageJpgFile source;

    public JobEventImageJpgInfo() {
        super();
    }

    public JobEventImageJpgInfo(
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
        if (!(o instanceof JobEventImageJpgInfo)) return false;
        if (!super.equals(o)) return false;
        JobEventImageJpgInfo that = (JobEventImageJpgInfo) o;
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
