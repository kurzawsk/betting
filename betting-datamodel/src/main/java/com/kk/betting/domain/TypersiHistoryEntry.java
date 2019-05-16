package com.kk.betting.domain;

import com.kk.betting.domain.converter.YearMonthAttributeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.YearMonth;

/**
 * Created by KK on 2017-08-30.
 */

@Entity
@Table(name = "TYPERSI_HISTORY_ENTRY")
public class TypersiHistoryEntry extends ManagedEntityImp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "BETTOR", nullable = false)
    private String bettor;

    @Column(name = "WON_BETS", nullable = false)
    private int wonBets;

    @Column(name = "LOST_BETS", nullable = false)
    private int lostBets;

    @Column(name = "AVG_ODD", precision = 9, scale = 2, nullable = false)
    private BigDecimal avgOdd;

    @Column(name = "PROFIT", precision = 9, scale = 2, nullable = false)
    private BigDecimal profit;

    @Column(name = "MONTH", nullable = false)
    @Convert(converter = YearMonthAttributeConverter.class)
    private YearMonth month;

    public String getBettor() {
        return bettor;
    }

    public void setBettor(String bettor) {
        this.bettor = bettor;
    }

    public int getWonBets() {
        return wonBets;
    }

    public void setWonBets(int wonBets) {
        this.wonBets = wonBets;
    }

    public int getLostBets() {
        return lostBets;
    }

    public void setLostBets(int lostBets) {
        this.lostBets = lostBets;
    }

    public BigDecimal getAvgOdd() {
        return avgOdd;
    }

    public void setAvgOdd(BigDecimal avgOdd) {
        this.avgOdd = avgOdd;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }
}
