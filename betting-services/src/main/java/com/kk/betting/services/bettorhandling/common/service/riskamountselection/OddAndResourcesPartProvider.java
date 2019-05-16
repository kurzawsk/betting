package com.kk.betting.services.bettorhandling.common.service.riskamountselection;

import java.math.BigDecimal;

/**
 * Created by KK on 2017-08-05.
 */
public class OddAndResourcesPartProvider extends RiskAmountProvider {

    private BigDecimal part;

    public BigDecimal getPart() {
        return part;
    }

    public void setPart(BigDecimal part) {
        this.part = part;
    }


    @Override
    protected BigDecimal getRawValue(BigDecimal currentAvailableResources, BigDecimal odd) {
        return currentAvailableResources.multiply(part).divide(odd);
    }
}
