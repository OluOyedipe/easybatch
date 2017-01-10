package com.oreilly.batch.listeners

import groovy.util.logging.Slf4j
import org.springframework.batch.core.annotation.AfterRead
import org.springframework.batch.core.annotation.BeforeRead
import org.springframework.stereotype.Component

import javax.batch.api.chunk.listener.ItemReadListener
import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * Created by oo185005 on 1/8/17.
 */
@Component
@Slf4j
class InstrumentedItemReadListener implements ItemReadListener{
    Instant now

    @Override
    @BeforeRead
    void beforeRead() throws Exception {
        now = Instant.now()
    }

    @Override
    @AfterRead
    void afterRead(Object item) throws Exception {
        log.info "Read took ${ChronoUnit.MILLIS.between(now,Instant.now())}"
    }

    @Override
    void onReadError(Exception ex) throws Exception {

    }
}
