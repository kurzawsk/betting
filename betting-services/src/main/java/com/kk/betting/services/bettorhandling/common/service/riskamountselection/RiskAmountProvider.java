package com.kk.betting.services.bettorhandling.common.service.riskamountselection;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by KK on 2017-08-05.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class RiskAmountProvider {

    public final Optional<BigDecimal> get(BigDecimal currentAvailableResources, BigDecimal odd) {
        BigDecimal proposedRiskAmount = getRawValue(currentAvailableResources, odd);
        if (currentAvailableResources.compareTo(proposedRiskAmount) >= 0) {
            return Optional.of(proposedRiskAmount);
        }

        return Optional.empty();
    }

    protected abstract BigDecimal getRawValue(BigDecimal currentAvailableResources, BigDecimal odd);
}
