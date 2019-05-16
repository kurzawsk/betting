package com.kk.betting.services.common.service;

/**
 * Created by KK on 2017-07-13.
 */
public interface Converter<T,R> {

    R convertToDTO(T domain);
    T convertToDomain(R dto);
}
