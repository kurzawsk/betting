package com.kk.betting.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by KK on 2017-09-11.
 */
public class AlignBettorResourcesEventDTO implements Serializable {

    public Long bettorId;
    public BigDecimal amount;

    public Long getBettorId() {
        return bettorId;
    }

    public void setBettorId(Long bettorId) {
        this.bettorId = bettorId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
