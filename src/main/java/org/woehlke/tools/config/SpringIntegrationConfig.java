package org.woehlke.tools.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@EnableIntegration
@EnableBatchProcessing
public class SpringIntegrationConfig {

    public static final String LOGBUCH_QUEUE            = "org.woehlke,tools.logbuch.queue";
    public static final String LOGBUCH_UPDATED_QUEUE    = "org.woehlke,tools.logbuch.updated.queue";

    public static final String RECEIVER_IN = "receiver.in.";
}
