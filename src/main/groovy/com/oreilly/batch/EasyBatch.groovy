package com.oreilly.batch

import groovy.util.logging.Slf4j
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.launch.JobOperator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * Created by oo185005 on 12/10/16.
 */
@Slf4j
@SpringBootApplication
@EnableBatchProcessing
class EasyBatch implements ApplicationRunner {

    @Autowired
    JobOperator jobOperator

    static void main(String[] args) {
        SpringApplication.run(EasyBatch, args)
    }

    @Override
    void run(ApplicationArguments args) throws Exception {
        List<String> jobsToRun = args.getOptionValues('job')

        if (jobsToRun?.size() > 0) {
            log.info "Running jobs $jobsToRun"
        }
        else {
            log.warn "No jobs found to run!"
        }

        jobsToRun.each {
            jobOperator.startNextInstance(it)

        }
    }
}
