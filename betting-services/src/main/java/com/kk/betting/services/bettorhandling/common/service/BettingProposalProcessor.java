package com.kk.betting.services.bettorhandling.common.service;

import com.google.common.collect.Lists;
import com.kk.betting.domain.Bettor;
import com.kk.betting.domain.Bookmaker;
import com.kk.betting.domain.ManagedEntityImp;
import com.kk.betting.domain.MatchOdd;
import com.kk.betting.dto.MatchBetProposalDTO;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.bettorhandling.common.service.bettorlogic.BettorLogic;
import com.kk.betting.services.common.service.AbstractBettingEventProcessor;
import com.kk.betting.services.common.service.ObjectMapperFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by KK on 2017-05-06.
 */
@Named
@Singleton
public class BettingProposalProcessor extends AbstractBettingEventProcessor {

    private static final Log log = LogFactory.getLog(BettingProposalProcessor.class);

    private List<Long> activeBettorsIds;
    private Map<Long, BigDecimal> bettorsMinAmountsToBet;
    private Map<Long, BettorLogic> bettorsLogic;

    @PostConstruct
    public void init() {
        List<? extends Bettor> activeBettors = bettorDao.getActiveBettors();
        activeBettorsIds = activeBettors.stream().map(Bettor::getId).collect(Collectors.toList());
        bettorsMinAmountsToBet = activeBettors.stream().collect(Collectors.toMap(ManagedEntityImp::getId, b -> b.getBookmaker().getMinimalAmountToBet()));
        bettorsLogic = activeBettors.stream()
                .collect(Collectors.toMap(ManagedEntityImp::getId, b -> ObjectMapperFactory.get().convertValue(b.getLogicParameters(), BettorLogic.class)));
    }

    public List<MatchBetProposalDTO> processBettingProposal(final MatchBetProposalRawDTO matchBetProposalRawDTO) {
        List<MatchBetProposalDTO> result = Lists.newArrayList();
        List<Long> bettorsToProcess = Lists.newArrayList(activeBettorsIds);
        while (!bettorsToProcess.isEmpty()) {
            List<Long> recentlyProcessedBettors = Lists.newArrayList();
            for (Long bettorId : bettorsToProcess) {
                Bettor bettor = bettorDao.findRefreshedById(bettorId);
                List<MatchBetProposalDTO> bettorProposalHolder = Lists.newArrayListWithCapacity(1);
                if (bettor.isBettingInProgress()) {
                    log.info("Skipping processBettingProposal for bettor: " + bettor.getId() + " match:" + matchBetProposalRawDTO.getMatchId() + "as its in progress");
                    continue;
                }
                bettor.setBettingInProgress(true);
                bettorDao.persist(bettor);
                if (!bettorDao.isMatchAlreadyBetByBettor(matchDao.findById(matchBetProposalRawDTO.getMatchId()), bettor)) {
                   alignMatchOddDiscrepancies(bettor.getBookmaker(), matchBetProposalRawDTO)
                            .flatMap(alignedProposal -> bettorsLogic.get(bettor.getId()).processBetProposal(alignedProposal, bettor))
                            .flatMap(bettorProposal -> filterProposalsBelowMinimalAllowedBookmakerBetAmount(bettorProposal))
                            .ifPresent(bettorProposalHolder::add);
                    if (bettorProposalHolder.isEmpty()) {
                        bettor.setBettingInProgress(false);
                        bettorDao.persist(bettor);
                    } else {
                        result.addAll(bettorProposalHolder);
                    }
                } else {
                    bettor.setBettingInProgress(false);
                    bettorDao.persist(bettor);
                }
                recentlyProcessedBettors.add(bettorId);
            }
            bettorsToProcess.removeAll(recentlyProcessedBettors);
        }
        log.info("matchBetProposalRawDTO = " + matchBetProposalRawDTO + " resulted in: " + result.size() + " MatchBetProposalDTOs sent. " +
                "Bettors which accepted: " + result.stream().map(MatchBetProposalDTO::getBettorId).collect(Collectors.toList()));
        return result;
    }

    private Optional<MatchBetProposalRawDTO> alignMatchOddDiscrepancies(Bookmaker bookmaker, MatchBetProposalRawDTO originalMatchBetProposalRawDTO) {
        Optional<MatchOdd> latestTrackedOdd = matchOddDao.getLatestMatchOdd(originalMatchBetProposalRawDTO.getMatchId(), bookmaker.getId());
        MatchOdd.Type expectedResult = MatchOdd.Type.valueOf(originalMatchBetProposalRawDTO.getExpectedMatchResult());

        if (!(latestTrackedOdd.isPresent() && Objects.nonNull(latestTrackedOdd.get().getOdd(expectedResult)))) {
            log.warn("Latest odd: " + expectedResult + " is null for match: " + originalMatchBetProposalRawDTO.getMatchId()+", bookmaker: " + bookmaker.getLabel());
            return Optional.empty();
        } else if (latestTrackedOdd.get().getOdd(expectedResult).compareTo(originalMatchBetProposalRawDTO.getOdd()) != 0) {
            log.debug("Latest tracked odd and odd contained in proposal differ (type: " + expectedResult + "): trackedOdd: " + latestTrackedOdd.get().getOdd(expectedResult) + " proposalOdd: " + originalMatchBetProposalRawDTO.getOdd());
            return Optional.of(MatchBetProposalRawDTO.builder().
                    setMatchId(originalMatchBetProposalRawDTO.getMatchId()).
                    setExpectedMatchResult(originalMatchBetProposalRawDTO.getExpectedMatchResult()).setOdd(latestTrackedOdd.get().getOdd(expectedResult)).
                    setProposalSource(originalMatchBetProposalRawDTO.getProposalSource()).build());
        }
        return Optional.of(originalMatchBetProposalRawDTO);

    }

    private Optional<MatchBetProposalDTO> filterProposalsBelowMinimalAllowedBookmakerBetAmount(MatchBetProposalDTO matchBetProposalDTO) {
        if (bettorsMinAmountsToBet.get(matchBetProposalDTO.getBettorId()).compareTo(matchBetProposalDTO.getRiskAmount()) < 0) {
            return Optional.of(matchBetProposalDTO);
        }
        log.info("Refusing bettor proposal: " + matchBetProposalDTO + " as risk amount is lower than minimal for its bettor: " + bettorsMinAmountsToBet.get(matchBetProposalDTO.getBettorId()));
        return Optional.empty();
    }
}
