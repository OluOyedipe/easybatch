package com.oreilly.batch.error
/**
 * Created by oo185005 on 1/8/17.
 */
class JobNotFoundException extends Exception {
    JobNotFoundException(String message) {
        super(message)
    }

    JobNotFoundException(String message, Throwable cause) {
        super(message, cause)
    }
}
