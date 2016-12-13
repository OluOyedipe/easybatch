package com.oreilly.batch.configuration

import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor
import org.springframework.batch.core.converter.DefaultJobParametersConverter
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.JobOperator
import org.springframework.batch.core.launch.support.SimpleJobOperator
import org.springframework.batch.core.repository.JobRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * Created by oo185005 on 12/10/16.
 */
@Configuration
class BaseJobConfiguration implements ApplicationContextAware {

    @Autowired
    JobLauncher jobLauncher

    @Autowired
    JobRepository jobRepository

    @Autowired
    JobExplorer jobExplorer

    @Autowired
    JobRegistry jobRegistry

    ApplicationContext applicationContext

    @Bean
    JobOperator jobOperator() {
        new SimpleJobOperator().with {
            setJobLauncher(this.jobLauncher)
            setJobParametersConverter(new DefaultJobParametersConverter())
            setJobRepository(this.jobRepository)
            setJobExplorer(this.jobExplorer)
            setJobRegistry(this.jobRegistry)

            afterPropertiesSet()

            it
        }
    }

    @Bean
    JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
        new JobRegistryBeanPostProcessor().with {
            it.setJobRegistry(this.jobRegistry)
            setBeanFactory(this.applicationContext.getAutowireCapableBeanFactory())

            afterPropertiesSet()

            it
        }
    }

    @Override
    void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext
    }
}
