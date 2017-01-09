package com.oreilly.batch.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Created by oo185005 on 1/8/17.
 */
@ControllerAdvice(basePackages = 'com.oreilly.batch')
class JobNotFoundMapper {

    @ExceptionHandler(JobNotFoundException)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = 'Job with name not found')
    void handleNotFound() {

    }
}
