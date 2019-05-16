package com.kk.betting.services.common.service;

import java.util.Map;

/**
 * Created by KK on 2017-09-02.
 */
public interface CustomJobLogic<T> {

    void init(Map<String, Object> parameters);

    T run();

}
