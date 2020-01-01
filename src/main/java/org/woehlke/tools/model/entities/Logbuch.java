package org.woehlke.tools.model.entities;


import org.woehlke.tools.model.config.JobEventSignal;
import org.woehlke.tools.model.config.JobEventType;
import org.woehlke.tools.model.common.JobEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

import static org.woehlke.tools.model.common.JobEventDiscriminatorValue.LOGBUCH;


@Entity
@DiscriminatorValue(LOGBUCH)
public class Logbuch extends JobEvent implements Serializable {

    @NotBlank
    @Column(length=65000, columnDefinition = "TEXT")
    private String line;

    @NotBlank
    @Column(length=65000, columnDefinition = "TEXT")
    private String category;

    private Logbuch() {
        super();
        this.line = "UNDEFINED";
        this.category = "UNDEFINED";
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
        this.category = "UNDEFINED";
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
