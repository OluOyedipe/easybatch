package com.oreilly.batch.listeners

import groovy.util.logging.Slf4j
import org.springframework.batch.core.annotation.AfterRead
import org.springframework.batch.core.annotation.BeforeRead
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.metrics.GaugeService
import org.springframework.stereotype.Component

import javax.batch.api.chunk.listener.ItemReadListener
import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * Created by oo185005 on 1/8/17.
 */
class InstrumentedItemReadListener implements ItemReadListener{
    Instant now
    String jobName

    InstrumentedItemReadListener(String jobName) {
        this.jobName = jobName
    }

    @Autowired
    GaugeService gaugeService

    @Override
    @BeforeRead
    void beforeRead() throws Exception {
        now = Instant.now()
    }

    @Override
    @AfterRead
    void afterRead(Object item) throws Exception {
        gaugeService.submit("histogram.${jobName}.read.step", ChronoUnit.MILLIS.between(now,Instant.now()))
    }

    @Override
    void onReadError(Exception ex) throws Exception {

    }
}
