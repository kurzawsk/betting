package com.kk.betting.test.util;

import javax.transaction.SystemException;

/**
 * Created by KK on 2017-06-05.
 */
public interface Validator {
    public void validate() throws AssertionError;
}
