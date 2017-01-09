package com.oreilly.batch.controller

import com.oreilly.batch.error.JobNotFoundException
import org.springframework.batch.core.launch.NoSuchJobExecutionException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

/**
 * Created by oo185005 on 1/8/17.
 */
@RestController
@RequestMapping('/batch')
class ExceptionThrowingController {
    @PostMapping('/fake/{jobName}')
    @ResponseStatus(HttpStatus.ACCEPTED)
    Long launch(@PathVariable String jobName) {
        throw new NoSuchJobExecutionException('This is a fake request!')
    }
}
