package com.kk.betting.services.bettorhandling.typersi.dto;

/**
 * Created by KK on 2017-08-30.
 */
public class TypersiBettorHistoryEntryDTO {

    private String bettor;
    private String wonBets;
    private String lostBets;
    private String avgOdd;
    private String profit;

    public String getBettor() {
        return bettor;
    }

    public void setBettor(String bettor) {
        this.bettor = bettor;
    }

    public String getWonBets() {
        return wonBets;
    }

    public void setWonBets(String wonBets) {
        this.wonBets = wonBets;
    }

    public String getLostBets() {
        return lostBets;
    }

    public void setLostBets(String lostBets) {
        this.lostBets = lostBets;
    }

    public String getAvgOdd() {
        return avgOdd;
    }

    public void setAvgOdd(String avgOdd) {
        this.avgOdd = avgOdd;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "TypersiBettorHistoryEntryDTO{" +
                "bettor='" + bettor + '\'' +
                ", wonBets='" + wonBets + '\'' +
                ", lostBets='" + lostBets + '\'' +
                ", avgOdd='" + avgOdd + '\'' +
                ", profit='" + profit + '\'' +
                '}';
    }
}
