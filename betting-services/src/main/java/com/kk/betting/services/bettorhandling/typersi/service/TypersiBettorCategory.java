package com.kk.betting.services.bettorhandling.typersi.service;

/**
 * Created by KK on 2017-09-03.
 */
enum TypersiBettorCategory {

    PROFIT(120), EFFICIENCY(120);

    private int limit;

    TypersiBettorCategory(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }
}
