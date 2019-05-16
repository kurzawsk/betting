package com.kk.betting.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by KK on 2016-10-30.
 */
public class MatchBetProposalRawDTO implements Serializable {

    private Long matchId;
    private String expectedMatchResult;
    private BigDecimal odd;
    private String proposalSource;

    private MatchBetProposalRawDTO(Long matchId, String expectedMatchResult,BigDecimal odd, String proposalSource) {
        this.matchId = matchId;
        this.expectedMatchResult = expectedMatchResult;
        this.odd = odd;
        this.proposalSource = proposalSource;
    }

    public static MatchBetProposalDTOBuilder builder(){
        return new MatchBetProposalDTOBuilder();
    }

    public long getMatchId() {
        return matchId;
    }

    public String getExpectedMatchResult() {
        return expectedMatchResult;
    }

    public BigDecimal getOdd() {
        return odd;
    }

    public String getProposalSource() {
        return proposalSource;
    }

    @Override
    public String toString() {
        return "MatchBetProposalRawDTO{" +
                "matchId=" + matchId +
                ", expectedMatchResult='" + expectedMatchResult + '\'' +
                ", odd=" + odd +
                ", proposalSource='" + proposalSource + '\'' +
                '}';
    }

    public static class MatchBetProposalDTOBuilder{

        private Long matchId;
        private String expectedMatchResult;
        private BigDecimal odd;
        private String proposalSource;

        public MatchBetProposalRawDTO build(){
            return new MatchBetProposalRawDTO( matchId,    expectedMatchResult,  odd, proposalSource);
        }

        public MatchBetProposalDTOBuilder setMatchId(Long matchId) {
            this.matchId = matchId;
            return this;
        }


        public MatchBetProposalDTOBuilder setExpectedMatchResult(String expectedMatchResult) {
            this.expectedMatchResult = expectedMatchResult;
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
