package com.kk.betting.services.bettorhandling.common.service.riskamountselection;

import java.math.BigDecimal;

/**
 * Created by KK on 2017-08-05.
 */
public class FixedValueProvider extends RiskAmountProvider {

    private BigDecimal fixedRiskAmount;

    @Override
    protected BigDecimal getRawValue(BigDecimal currentAvailableResources, BigDecimal odd) {
        return fixedRiskAmount;
    }

    public BigDecimal getFixedRiskAmount() {
        return fixedRiskAmount;
    }

    public void setFixedRiskAmount(BigDecimal fixedRiskAmount) {
        this.fixedRiskAmount = fixedRiskAmount;
    }
}
