package org.woehlke.tools.model.db.entities;


import org.woehlke.tools.config.db.JobEventSignal;
import org.woehlke.tools.config.db.JobEventType;
import org.woehlke.tools.model.db.common.JobEvent;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static org.woehlke.tools.model.db.common.JobEventDiscriminatorValue.LOGBUCH;


@Entity
@DiscriminatorValue(LOGBUCH)
public class Logbuch  extends JobEvent implements Serializable {

    @Column
    private String line;

    @Column
    private String category;

    public Logbuch() {
        super();
    }

    public Logbuch(
        String line,
        String category,
        Job myJob,
        JobEventType jobEventType,
        JobEventSignal jobEventSignal
    ) {
        super(myJob, jobEventType, jobEventSignal);
        this.line = line;
        this.category = category;
    }

    public Logbuch(
        String line,
        Job myJob,
        JobEventType jobEventType,
        JobEventSignal jobEventSignal
    ) {
        super(myJob, jobEventType, jobEventSignal);
        this.line = line;
        this.category = "ALL";
    }

    public void setLine(String line) {
        if(line.length()>255){
            this.line = line.substring(0,255);
        } else {
            this.line = line;
        }
    }

    public void setCategory(String category) {
        if((category != null) && (category.length() > 255)) {
            this.category = category.substring(0, 255);
        } else {
            this.category = category;
        }
    }

    public String getLine() {
        return line;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Logbuch)) return false;
        if (!super.equals(o)) return false;
        Logbuch logbuch = (Logbuch) o;
        return getLine().equals(logbuch.getLine()) &&
            getCategory().equals(logbuch.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLine(), getCategory());
    }

    @Override
    public String toString() {
        return "Logbuch{" +
            "line='" + line + '\'' +
            ", category='" + category + '\'' +
            "} " + super.toString();
    }
}
