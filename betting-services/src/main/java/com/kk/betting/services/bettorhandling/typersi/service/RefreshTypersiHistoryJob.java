package com.kk.betting.services.bettorhandling.typersi.service;

import com.kk.betting.services.common.service.CustomJobLogic;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by KK on 2017-09-02.
 */
public class RefreshTypersiHistoryJob implements CustomJobLogic<Void> {

    private static final String NO_OF_MONTHS_KEY_NAME = "MONTHS";
    private int noOfMonths;

    @Inject
    private TypersiHistoryService typersiHistoryService;

    @Override
    public void init(Map<String, Object> parameters) {
        noOfMonths = (int) parameters.get(NO_OF_MONTHS_KEY_NAME);
    }

    @Override
    public Void run() {
        typersiHistoryService.updateTypersiHistory(noOfMonths);
        return null;
    }
}
