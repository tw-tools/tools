package org.woehlke.tools.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@EnableRabbit
@EnableIntegration
@EnableBatchProcessing
public class ApplicationConfig {

    public static final String QUEUE_NAME = "org.woehlke,tools.logging2db";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }
}
