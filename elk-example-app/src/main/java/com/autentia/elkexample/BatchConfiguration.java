package com.autentia.elkexample;

import com.autentia.elkexample.batch.item.LogItemProcessor;
import com.autentia.elkexample.batch.item.LogItemWriter;
import com.autentia.elkexample.batch.item.StaticItemReader;
import com.autentia.elkexample.domain.Item;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private static final Log LOG = LogFactory.getLog(BatchConfiguration.class);

    @Bean
    @StepScope
    public ItemReader<Item> reader(@Value("#{jobParameters[duration]}") Integer duration) {
        LOG.info("Creating item reader with duration: " + duration);
        StaticItemReader reader = new StaticItemReader();
        reader.setDuration(duration);
        return reader;
    }

    @Bean
    public ItemProcessor<Item, Item> processor() {
        return new LogItemProcessor();
    }

    @Bean
    public ItemWriter<Item> writer() {
        return new LogItemWriter();
    }

    @Bean
    public Job exampleJob(JobBuilderFactory jobs, Step s1) {
        return jobs.get("exampleJob")
            .incrementer(new RunIdIncrementer())
            .flow(s1)
            .end()
            .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Item> reader, ItemWriter<Item> writer, ItemProcessor<Item, Item> processor) {
        return stepBuilderFactory.get("step1")
            .<Item, Item>chunk(1)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

}
