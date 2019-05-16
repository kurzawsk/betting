package com.kk.betting.services.bettorhandling.common.service.bettorlogic;

import java.math.BigDecimal;

/**
 * Created by KK on 2017-08-05.
 */
public class SupportedOddRangesProvider {


    private BigDecimal lowerLimit;
    private BigDecimal upperLimit;

    public BigDecimal getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(BigDecimal lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public BigDecimal getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(BigDecimal upperLimit) {
        this.upperLimit = upperLimit;
    }

    public boolean isInRange(BigDecimal value) {
        return lowerLimit.compareTo(value) <= 0 && upperLimit.compareTo(value) > 0;
    }

}
