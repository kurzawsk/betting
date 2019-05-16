package com.kk.betting.services.common.dao;

import java.util.List;

/**
 * Created by KK on 2017-06-25.
 */
public interface GenericDao<T, K> {
    T findById(K id);

    T findRefreshedById(K id);

    void refresh(T entity);

    void persist(T entity);

    void persist(Iterable<? extends T> entity);

    void remove(T entity);

    List<? extends T> findAll();
}
