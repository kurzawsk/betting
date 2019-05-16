package com.kk.betting.services.bettorhandling.common.service.alignbettorresources;

import com.kk.betting.services.common.service.CustomJobLogic;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by KK on 2017-09-11.
 */
public class AlignBettorResourcesJob implements CustomJobLogic<Void> {

    @Inject
    private AlignBettorResourcesService alignBettorResourcesService;

    @Override
    public void init(Map<String, Object> parameters) {

    }

    @Override
    public Void run() {
        alignBettorResourcesService.withDrawMoney();
        return null;
    }
}
