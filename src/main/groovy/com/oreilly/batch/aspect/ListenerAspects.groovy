package com.oreilly.batch.aspect

import com.oreilly.batch.listeners.InstrumentedItemReadListener
import groovy.util.logging.Slf4j
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager

/**
 * Created by oo185005 on 1/8/17.
 */
@Component
@Aspect
@Slf4j
class ListenerAspects {

    @Autowired
    JobRepository jobRepository

    @Autowired
    PlatformTransactionManager transactionManager

    @Autowired
    InstrumentedItemReadListener readListener

//    @Around('execution(* org.springframework.batch.core.configuration.annotation.StepBuilderFactory.get(..))')
    StepBuilder addInstrumentedStepListeners(ProceedingJoinPoint joinPoint) {
        log.info "Executing ${joinPoint.signature.name}"
//        joinPoint.proceed()
        new StepBuilder(joinPoint.args[0] as String)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .listener(readListener)
    }

}
