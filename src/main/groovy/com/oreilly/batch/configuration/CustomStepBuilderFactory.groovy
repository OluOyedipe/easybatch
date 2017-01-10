package com.oreilly.batch.configuration

import com.oreilly.batch.listeners.InstrumentedItemReadListener
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.builder.SimpleStepBuilder
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by oo185005 on 1/9/17.
 */
class CustomStepBuilderFactory <I, O> {
    StepBuilderFactory stepBuilderFactory
    int chunkSize


    @Autowired
    InstrumentedItemReadListener instrumentedItemReadListener

    CustomStepBuilderFactory(StepBuilderFactory stepBuilderFactory, int chunkSize) {
        this.stepBuilderFactory = stepBuilderFactory
        this.chunkSize = chunkSize
    }

    SimpleStepBuilder<I, O> get(String jobName) {
        stepBuilderFactory
                .get(jobName)
                .<I, O>chunk(chunkSize)
                .listener(instrumentedItemReadListener)
    }

}
