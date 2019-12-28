package org.woehlke.tools.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;

//@Configuration
//@EnableIntegration
//@EnableBatchProcessing
public class SpringBatchConfig {


    /*
    @Bean
    public Job scaleImagesJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("scaleImagesJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(traverseDirs)
            .flow(traverseFiles)
            .flow(traverseFiles)
            .flow(scaleOneImage)
            .end()
            .build();
    }

    @Bean
    public Step traverseDirs(JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory.get("traverseDirs")
            .<Person, Person> chunk(10)
            .reader(reader())
            .processor(processor())
            .writer(writer)
            .build();
    }

    @Bean
    public Step traverseFiles(JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory.get("traverseFiles")
            .<Person, Person> chunk(10)
            .reader(reader())
            .processor(processor())
            .writer(writer)
            .build();
    }

    @Bean
    public Step scaleOneImage(JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory.get("traverseFiles")
            .<Person, Person> chunk(10)
            .reader(reader())
            .processor(processor())
            .writer(writer)
            .build();
    }

*/
}
