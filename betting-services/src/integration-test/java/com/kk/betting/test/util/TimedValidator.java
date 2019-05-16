package com.kk.betting.test.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by KK on 2017-06-05.
 */
public class TimedValidator implements Validator {

    private int noOfAttempts;
    private long timeout;
    private Validator validator;

    public TimedValidator(int noOfAttempts, long timeout, Validator validator) {
        this.noOfAttempts = noOfAttempts;
        this.timeout = timeout;
        this.validator = validator;
    }


    public void validate() {
        boolean passed = false;
        AssertionError latestError = null;

        while (noOfAttempts > 0 && !passed) {
            try {
                TimeUnit.MILLISECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                validator.validate();
                passed = true;
            } catch (AssertionError e) {
                latestError = e;
                passed = false;
            } finally {
                noOfAttempts--;
            }
        }

        if (!passed) {
            throw latestError;
        }

    }

}
