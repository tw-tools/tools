package org.woehlke.tools.model.db.entities.jobevents;

import org.woehlke.tools.config.db.JobEventSignal;
import org.woehlke.tools.model.db.entities.Job;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.File;

import static org.woehlke.tools.model.db.entities.parts.JobEventDiscriminatorValue.RENAMED_ONE_FILE;

@Entity
@DiscriminatorValue(RENAMED_ONE_FILE)
public class RenamedOneFile extends RenamedOneDirectory {

    public RenamedOneFile() {
        super();
    }

    public RenamedOneFile(
        JobEventSignal jobEventSignal,
        Job myJob,
        File source,
        File target
    ) {
        super(jobEventSignal, myJob, source, target);
    }

    @Override
    public String toString() {
        return "JobEventRenamedOneFile{} " + super.toString();
    }
}
