package com.kk.betting.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by KK on 2016-10-30.
 */
public class MatchBetProposalDTO  implements Serializable {

    private Long matchId;
    private Long bettorId;
    private String expectedMatchResult;
    private BigDecimal riskAmount;
    private BigDecimal odd;
    private String proposalSource;

    private  MatchBetProposalDTO(Long matchId, Long bettorId, String expectedMatchResult, BigDecimal riskAmount, BigDecimal odd,String proposalSource) {
        this.matchId = matchId;
        this.bettorId = bettorId;
        this.expectedMatchResult = expectedMatchResult;
        this.riskAmount = riskAmount;
        this.odd = odd;
        this.proposalSource = proposalSource;
    }

    public static MatchBetProposalDTOBuilder builder(){
        return new MatchBetProposalDTOBuilder();
    }


    public long getMatchId() {
        return matchId;
    }

    public long getBettorId() {
        return bettorId;
    }


    public String getExpectedMatchResult() {
        return expectedMatchResult;
    }

    public BigDecimal getRiskAmount() {
        return riskAmount;
    }

    public BigDecimal getOdd() {
        return odd;
    }

    public String getProposalSource() {
        return proposalSource;
    }

    @Override
    public String toString() {
        return "MatchBetProposalDTO{" +
                "matchId=" + matchId +
                ", bettorId=" + bettorId +
                ", expectedMatchResult='" + expectedMatchResult + '\'' +
                ", riskAmount=" + riskAmount +
                ", odd=" + odd +
                ", proposalSource='" + proposalSource + '\'' +
                '}';
    }

    public static class MatchBetProposalDTOBuilder{

        private Long matchId;
        private Long bettorId;
        private String expectedMatchResult;
        private BigDecimal riskAmount;
        private BigDecimal odd;
        private String proposalSource;

        public MatchBetProposalDTO build(){
            return new MatchBetProposalDTO( matchId,  bettorId,  expectedMatchResult,  riskAmount,  odd, proposalSource);
        }

        public MatchBetProposalDTOBuilder setMatchId(Long matchId) {
            this.matchId = matchId;
            return this;
        }

        public MatchBetProposalDTOBuilder setBettorId(Long bettorId) {
            this.bettorId = bettorId;
            return this;
        }

        public MatchBetProposalDTOBuilder setExpectedMatchResult(String expectedMatchResult) {
            this.expectedMatchResult = expectedMatchResult;
            return this;
        }

        public MatchBetProposalDTOBuilder setRiskAmount(BigDecimal riskAmount) {
            this.riskAmount = riskAmount;
            return this;
        }

        public MatchBetProposalDTOBuilder setOdd(BigDecimal odd) {
            this.odd = odd;
            return this;
        }

        public MatchBetProposalDTOBuilder setProposalSource(String proposalSource) {
            this.proposalSource = proposalSource;
            return this;
        }
    }

}
