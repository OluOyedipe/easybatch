package com.oreilly.batch.controller

import org.springframework.batch.core.launch.JobOperator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * Created by oo185005 on 1/5/17.
 */
@RestController
@RequestMapping('/batch')
class JobLaunchingController {

    @Autowired
    JobOperator jobOperator

    @PostMapping('/{jobName}')
    @ResponseStatus(HttpStatus.ACCEPTED)
    Long launch(@PathVariable String jobName) {
//        JobParameters jobParameters = new JobParametersBuilder().addString('jobName', jobName).toJobParameters()
        jobOperator.startNextInstance(jobName)
    }


}
