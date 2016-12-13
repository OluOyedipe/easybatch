package com.oreilly.batch.configuration

import com.oreilly.batch.domain.Customer
import groovy.util.logging.Slf4j
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.xml.StaxEventItemReader
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.oxm.xstream.XStreamMarshaller

/**
 * Created by oo185005 on 12/10/16.
 */
@Configuration
class XmlJobConfiguration {
    @Autowired
    JobBuilderFactory jobBuilderFactory

    @Autowired
    StepBuilderFactory stepBuilderFactory

    @Bean
    Job xmlJob() {
        jobBuilderFactory.get('xmlJob').start(xmlReadStep()).incrementer(new RunIdIncrementer()).build()
    }

    @Bean
    Step xmlReadStep() {
        stepBuilderFactory.get('xmlReadStep')
                .<Customer, Customer>chunk(10)
                .reader(customerXmlFileItemReader())
                .writer(customerItemWriter())
                .build()
    }

    @Bean
    StaxEventItemReader<Customer> customerXmlFileItemReader() {
        XStreamMarshaller unmarshaller = new XStreamMarshaller()
        Map<String, Class> aliases = [:]
        aliases.put('customer', Customer)

        unmarshaller.setAliases(aliases)
        StaxEventItemReader<Customer> reader = new StaxEventItemReader<>()

        reader.setResource(new ClassPathResource('customers.xml'))
        reader.setFragmentRootElementName('customer')
        reader.setUnmarshaller(unmarshaller)

        reader
    }
    @Bean
    ItemWriter<Customer> customerItemWriter() {
        return new ItemWriter<Customer>() {
            @Override
            void write(List<? extends Customer> items) throws Exception {
                def counter = 0
                items.each {
                    if (counter == 11) {
                        throw new IllegalStateException("Don't like 9")
                    }
                    println it.toString()
                    counter++
                }
            }
        }
    }


}
