package com.oreilly.batch.configuration

import com.oreilly.batch.domain.Customer
import com.oreilly.batch.domain.CustomerFieldSetMapper
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.MultiResourceItemReader
import org.springframework.batch.item.file.mapping.DefaultLineMapper
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource

/**
 * Created by oo185005 on 12/10/16.
 */

@Configuration
class MultipleFileJobConfiguration {
    @Autowired
    JobBuilderFactory jobBuilderFactory

    @Autowired
    StepBuilderFactory stepBuilderFactory

    @Autowired
    ItemWriter customerItemWriter

    @Value('classpath*:customer*.csv')
    Resource[] inputFiles

    @Bean
    Job multipleFileJob() {
        jobBuilderFactory.get('multipleFileJob').start(multipleFileReadStep()).incrementer(new RunIdIncrementer()).build()
    }

    @Bean
    Step multipleFileReadStep() {
        stepBuilderFactory.get('multipleFileReadStep')
                .<Customer, Customer> chunk(10)
                .reader(multiResourceItemReader())
                .writer(customerItemWriter)
                .build()
    }

    @Bean
    MultiResourceItemReader<Customer> multiResourceItemReader() {
        MultiResourceItemReader<Customer> reader = new MultiResourceItemReader<>()

        reader.setDelegate(customerCsvFileItemReader())
        reader.setResources(inputFiles)

        reader
    }

    @Bean
    FlatFileItemReader<Customer> customerCsvFileItemReader() {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>()
        DefaultLineMapper<Customer> customerLineMapper = new DefaultLineMapper<>()

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer()
        tokenizer.setNames(['id', 'firstName', 'lastName', 'birthdate'] as String[])

        customerLineMapper.lineTokenizer = tokenizer
        customerLineMapper.setFieldSetMapper(new CustomerFieldSetMapper())
        customerLineMapper.afterPropertiesSet()

        reader.setLineMapper(customerLineMapper)

        reader
    }


}
