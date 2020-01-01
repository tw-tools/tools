package org.woehlke.tools.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties("org.woehlke.tools.config")
@Valid
@Validated
public class ApplicationProperties {

    @NotNull private Integer width;
    @NotNull private Integer height;
    @NotNull private Boolean dryRun;
    @NotNull private Boolean dbActive;
    @NotNull private Integer imageTargetScale;
    @NotNull private Integer queueCapacity;

    public Integer getImageTargetScale() {
        return imageTargetScale;
    }

    public void setImageTargetScale(Integer imageTargetScale) {
        this.imageTargetScale = imageTargetScale;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getDryRun() {
        return dryRun;
    }

    public void setDryRun(Boolean dryRun) {
        this.dryRun = dryRun;
    }

    public Boolean getDbActive() {
        return dbActive;
    }

    public void setDbActive(Boolean dbActive) {
        this.dbActive = dbActive;
    }

    public Integer getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(Integer queueCapacity) {
        this.queueCapacity = queueCapacity;
    }
}
