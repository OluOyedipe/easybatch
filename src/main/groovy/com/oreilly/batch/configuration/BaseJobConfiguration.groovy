package com.oreilly.batch.configuration

import org.springframework.batch.admin.launch.JobLauncherSynchronizer
import org.springframework.batch.admin.web.RestControllerAdvice
import org.springframework.batch.core.configuration.JobRegistry
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor
import org.springframework.batch.core.converter.DefaultJobParametersConverter
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.JobOperator
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.batch.core.launch.support.SimpleJobOperator
import org.springframework.batch.core.repository.JobRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

import javax.sql.DataSource

/**
 * Created by oo185005 on 12/10/16.
 */
@Configuration
@Import(RestControllerAdvice)
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

    @Autowired
    DataSource dataSource

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

    @Bean
    JobLauncher getJobLauncher() {

        new SimpleJobLauncher().with {
            setJobRepository(this.jobRepository)
            setTaskExecutor(taskExecutor())

            afterPropertiesSet()

            it
        }
    }

    @Bean
    TaskExecutor taskExecutor() {
        new ThreadPoolTaskExecutor().with {
            corePoolSize = 2
            threadNamePrefix = 'easybatch-'
            maxPoolSize = 10

            it
        }
    }

//    @Bean
//    JobService jobService() {
//        new SimpleJobServiceFactoryBean().with {
//            setJobLauncher(this.jobLauncher)
//            setJobRepository(this.jobRepository)
//            setJobExplorer(this.jobExplorer)
//            setJobRegistry(this.jobRegistry)
//            setJobLocator(this.jobRegistry)
//            setTransactionManager(this.applicationContext.getBean(PlatformTransactionManager))
//            setDataSource(this.applicationContext.getBean(DataSource))
//
//            afterPropertiesSet()
//            it
//        }.object
//    }

    @Bean
    JobLauncherSynchronizer jobLauncherSynchronizer() {
        new JobLauncherSynchronizer().with {
            setJobRepository(this.jobRepository)
            setJobExplorer(this.jobExplorer)
            setJobLauncher(getJobLauncher())
            setJobRegistry(this.jobRegistry)
            addJobName('xmlJob')

            afterPropertiesSet()

            it
        }
    }

    @Override
    void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext
    }
}
