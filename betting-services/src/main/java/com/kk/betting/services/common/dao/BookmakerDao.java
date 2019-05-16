package com.kk.betting.services.common.dao;

import com.kk.betting.domain.Bookmaker;

/**
 * Created by KK on 2017-06-26.
 */
public class BookmakerDao extends BaseJpaDao<Bookmaker, Long> {
    public BookmakerDao() {
        super(Bookmaker.class);
    }
}
