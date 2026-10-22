package com.xgy.base.job;

import com.xgy.base.config.JobHelper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@EnableBatchProcessing
@Configuration
public class SampleBatchJobConfig extends JobHelper {

    @Bean
    public Job sampleJob() {
        return jobs.get("sampleJob")
                .start(sampleStep())
                .build();
    }

    @Bean
    public Step sampleStep() {
        return steps.get("sampleStep")
                .<String, String>chunk(batchSize)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ListItemReader<String> itemReader() {
        List<String> items = Arrays.asList("hello", "spring", "batch");
        return new ListItemReader<>(items);
    }

    @Bean
    public ItemProcessor<String, String> itemProcessor() {
        return String::toUpperCase;
    }

    @Bean
    public ItemWriter<String> itemWriter() {
        return items -> items.forEach(item -> System.out.println(">> " + item));
    }
}
