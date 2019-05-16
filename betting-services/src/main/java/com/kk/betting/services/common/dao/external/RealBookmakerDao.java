package com.kk.betting.services.common.dao.external;

import com.kk.betting.domain.MatchOdd;

import java.math.BigDecimal;

/**
 * Created by KK on 2017-05-07.
 */
public interface RealBookmakerDao {

    BigDecimal getAvailableResources();
    void betMatch(String matchIdentifier, BigDecimal riskAmount, MatchOdd.Type type , BigDecimal odd);
}
