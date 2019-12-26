package org.woehlke.tools.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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

    public static final String LOGBUCH_QUEUE       = "org.woehlke,tools.logbuch.queue";
    public static final String LOGBUCH_ROUTING_KEY = "org.woehlke,tools.logbuch.routing";
    public static final String LOGBUCH_EXCHANGE    = "org.woehlke,tools.logbuch.exchange";


    @Bean
    public TopicExchange getLogbuchExchange() {
        return new TopicExchange(LOGBUCH_EXCHANGE);
    }

    /* Binding between Exchange and Queue using routing key */
    @Bean
    public Binding declareBindingLogbuch() {
        return BindingBuilder.bind(logbuchQueue()).to(getLogbuchExchange()).with(LOGBUCH_ROUTING_KEY);
    }

    @Bean
    public Queue logbuchQueue() {
        return new Queue(LOGBUCH_QUEUE);
    }
}
