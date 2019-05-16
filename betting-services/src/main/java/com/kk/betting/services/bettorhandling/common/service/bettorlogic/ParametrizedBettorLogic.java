package com.kk.betting.services.bettorhandling.common.service.bettorlogic;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.kk.betting.domain.Bettor;
import com.kk.betting.domain.MatchOdd;
import com.kk.betting.dto.MatchBetProposalDTO;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.bettorhandling.common.service.riskamountselection.RiskAmountProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by KK on 2017-08-05.
 */
public class ParametrizedBettorLogic implements BettorLogic {

    private static Log log = LogFactory.getLog(ParametrizedBettorLogic.class);
    private static final String PROPOSAL_SOURCE_DELIMITER = ",";

    private RiskAmountProvider riskAmountProvider;
    private SupportedOddRangesProvider supportedOddRangesProvider;
    private EnumSet<MatchOdd.Type> supportedOddTypes;
    private Set<String> supportedProposalsSources;

    @Override
    public Optional<MatchBetProposalDTO> processBetProposal(MatchBetProposalRawDTO matchBetProposalRawDTO, Bettor bettor) {
        if (!Sets.intersection(supportedProposalsSources, ImmutableSet.copyOf(matchBetProposalRawDTO.getProposalSource().split(PROPOSAL_SOURCE_DELIMITER))).isEmpty() &&
                supportedOddTypes.contains(MatchOdd.Type.valueOf(matchBetProposalRawDTO.getExpectedMatchResult())) &&
                supportedOddRangesProvider.isInRange(matchBetProposalRawDTO.getOdd())) {
            Optional<BigDecimal> riskAmountHolder = riskAmountProvider.get(bettor.getAvailableResources(), matchBetProposalRawDTO.getOdd());
            if (riskAmountHolder.isPresent()) {
                return Optional.of(MatchBetProposalDTO.builder()
                        .setMatchId(matchBetProposalRawDTO.getMatchId())
                        .setBettorId(bettor.getId())
                        .setOdd(matchBetProposalRawDTO.getOdd())
                        .setExpectedMatchResult(matchBetProposalRawDTO.getExpectedMatchResult())
                        .setProposalSource(matchBetProposalRawDTO.getProposalSource())
                        .setRiskAmount(riskAmountHolder.get()).build());
            } else {
                log.warn("Bettor: " + bettor.getId() + " does not have enough resources to bet match: " + matchBetProposalRawDTO.getMatchId());
            }
        }

        return Optional.empty();
    }

    public RiskAmountProvider getRiskAmountProvider() {
        return riskAmountProvider;
    }

    public void setRiskAmountProvider(RiskAmountProvider riskAmountProvider) {
        this.riskAmountProvider = riskAmountProvider;
    }

    public SupportedOddRangesProvider getSupportedOddRangesProvider() {
        return supportedOddRangesProvider;
    }

    public void setSupportedOddRangesProvider(SupportedOddRangesProvider supportedOddRangesProvider) {
        this.supportedOddRangesProvider = supportedOddRangesProvider;
    }

    public EnumSet<MatchOdd.Type> getSupportedOddTypes() {
        return supportedOddTypes;
    }

    public void setSupportedOddTypes(EnumSet<MatchOdd.Type> supportedOddTypes) {
        this.supportedOddTypes = supportedOddTypes;
    }

    public Set<String> getSupportedProposalsSources() {
        return supportedProposalsSources;
    }

    public void setSupportedProposalsSources(Set<String> supportedProposalsSources) {
        this.supportedProposalsSources = supportedProposalsSources;
    }
}
