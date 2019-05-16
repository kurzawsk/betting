package com.kk.betting.services.bettorhandling.typersi.dto;

/**
 * Created by KK on 2017-08-02.
 */
public class TypersiMatchBetProposalDTO {

    private String bettor;
    private String time;
    private String match;
    private String proposedResult;
    private String odd;

    public String getBettor() {
        return bettor;
    }

    public void setBettor(String bettor) {
        this.bettor = bettor;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getProposedResult() {
        return proposedResult;
    }

    public void setProposedResult(String proposedResult) {
        this.proposedResult = proposedResult;
    }

    public String getOdd() {
        return odd;
    }

    public void setOdd(String odd) {
        this.odd = odd;
    }

    @Override
    public String toString() {
        return "TypersiMatchBetProposalDTO{" +
                "bettor='" + bettor + '\'' +
                ", time='" + time + '\'' +
                ", match='" + match + '\'' +
                ", proposedResult='" + proposedResult + '\'' +
                ", odd='" + odd + '\'' +
                '}';
    }
}
