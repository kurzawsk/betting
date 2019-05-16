package com.kk.betting.services.bettorhandling.common.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kk.betting.domain.*;
import com.kk.betting.domain.MatchOdd.Type;
import com.kk.betting.dto.AlignBettorResourcesEventDTO;
import com.kk.betting.dto.MatchBetProposalDTO;
import com.kk.betting.dto.MatchFinishEventDTO;
import com.kk.betting.services.common.dao.BettingEventDao;
import com.kk.betting.services.common.dao.BettorDao;
import com.kk.betting.services.common.dao.MatchDao;
import com.kk.betting.services.common.dao.external.RealBookmakerDao;
import com.kk.betting.services.common.service.AbstractBettingEventProcessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.kk.betting.domain.MatchResult.Type.NORMAL;
import static javax.ejb.ConcurrencyManagementType.BEAN;

/**
 * Created by KK on 2017-02-07.
 */
@Singleton
@ConcurrencyManagement(BEAN)
public class BettingMatchEventProcessor extends AbstractBettingEventProcessor {

    private static final Log log = LogFactory.getLog(BettingMatchEventProcessor.class);
    private static final BigDecimal MATCH_WITHDRAWN_ODD = new BigDecimal("1.00");

    @Inject
    private BettingEventDao bettingEventDao;

    @Inject
    private MatchDao matchDao;

    @Inject
    private BettorDao bettorDao;

    final Map<Long, Set<PendingMatchData>> pendingMatches = Maps.newConcurrentMap();
    final Map<Long, RealBookmakerDao> bettor2RealSystemBinding = Maps.newConcurrentMap();

    @PostConstruct
    public void init() {
        initPendingMatches();
        initBookmaker2RealSystemBinding();
    }

    @Lock(LockType.READ)
    public void processMatchFinishEvent(MatchFinishEventDTO[] dtos) {
        int i = 0;
        for (MatchFinishEventDTO dto : dtos) {
            Set<PendingMatchData> relevantBettorData = pendingMatches.get(dto.getMatchId());
            log.info("Match finish " + dto.getMatchId() + " was relevant to bettors: " + (relevantBettorData != null ? relevantBettorData.stream().map(PendingMatchData::getBettorId).collect(Collectors.toList()) : ""));

            if (relevantBettorData != null && !relevantBettorData.isEmpty()) {
                List<Bettor> bettorsToUpdate = Lists.newArrayList();
                List<BettingEvent> bettingEventsToAdd = Lists.newArrayList();
                Match match = matchDao.findById(dto.getMatchId());
                MatchResult result = MatchResult.fromString(dto.getMatchResult());
                MatchResult.Type resultType = result.asType();
                try {
                    if (resultType == NORMAL) {
                        for (PendingMatchData rdb : relevantBettorData) {
                            //Bettor bettor = bettorDao.findById(rdb.getBettorId());
                            Bettor bettor = waitForBettorToBeFreeAndLock(rdb.getBettorId());
                            if (isBetSuccessful(result, Type.valueOf(rdb.getExpectedResult()))) {
                                BigDecimal wonAmount = rdb.getRiskAmount().multiply(rdb.getOdd()).multiply(BigDecimal.ONE.subtract(bettor.getBookmaker().getTax()));
                                BettingEvent bettingSuccessfulEvent = createBettingEvent(match, bettor, BettingEvent.Type.BET_SUCCESSFUL, rdb.getProposalSource());
                                bettingSuccessfulEvent.setBetOdd(rdb.getOdd());
                                bettingSuccessfulEvent.setExpectedMatchResult(Type.valueOf(rdb.getExpectedResult()));
                                bettingSuccessfulEvent.setResourcesAvailableBeforeEvent(bettor.getAvailableResources());
                                bettor.addToResources(wonAmount);
                                bettingSuccessfulEvent.setResourcesAvailableAfterEvent(bettor.getAvailableResources());
                                bettor.setAvgResourcesSinceLastAlignment(calculateAvgResourcesAlignment(bettor, bettingSuccessfulEvent));
                                bettingEventsToAdd.add(bettingSuccessfulEvent);
                            } else {
                                BettingEvent bettingUnsuccessfulEvent = createBettingEvent(match, bettor, BettingEvent.Type.BET_UNSUCCESSFUL, rdb.getProposalSource());
                                bettingUnsuccessfulEvent.setResourcesAvailableBeforeEvent(bettor.getAvailableResources());
                                bettingUnsuccessfulEvent.setExpectedMatchResult(Type.valueOf(rdb.getExpectedResult()));
                                bettingUnsuccessfulEvent.setResourcesAvailableAfterEvent(bettor.getAvailableResources());
                                bettingUnsuccessfulEvent.setBetOdd(rdb.getOdd());
                                bettingEventsToAdd.add(bettingUnsuccessfulEvent);
                                bettor.setAvgResourcesSinceLastAlignment(calculateAvgResourcesAlignment(bettor, bettingUnsuccessfulEvent));
                            }
                            bettor.setBettingInProgress(false);
                            bettorsToUpdate.add(bettor);
                            saveMatchEventResults(bettorsToUpdate, bettingEventsToAdd);

                            pendingMatches.get(dto.getMatchId()).remove(rdb);
                            bettingEventsToAdd.clear();
                            bettorsToUpdate.clear();
                        }
                    } else {
                        for (PendingMatchData rdb : relevantBettorData) {
                            Bettor bettor = waitForBettorToBeFreeAndLock(rdb.getBettorId());
                            BettingEvent bettingMatchWithdrawnEvent = createBettingEvent(match, bettor, BettingEvent.Type.MATCH_WITHDRAWN, rdb.getProposalSource());
                            bettingMatchWithdrawnEvent.setBetOdd(MATCH_WITHDRAWN_ODD);
                            bettingMatchWithdrawnEvent.setExpectedMatchResult(Type.valueOf(rdb.getExpectedResult()));
                            bettingMatchWithdrawnEvent.setResourcesAvailableBeforeEvent(bettor.getAvailableResources());
                            bettor.addToResources(rdb.getRiskAmount());
                            bettingMatchWithdrawnEvent.setResourcesAvailableAfterEvent(bettor.getAvailableResources());
                            bettingMatchWithdrawnEvent.setProposalSource(rdb.getProposalSource());
                            bettor.setAvgResourcesSinceLastAlignment(calculateAvgResourcesAlignment(bettor, bettingMatchWithdrawnEvent));
                            bettor.setBettingInProgress(false);
                            bettorsToUpdate.add(bettor);
                            bettingEventsToAdd.add(bettingMatchWithdrawnEvent);
                            saveMatchEventResults(bettorsToUpdate, bettingEventsToAdd);
                            pendingMatches.get(dto.getMatchId()).remove(rdb);
                            bettingEventsToAdd.clear();
                            bettorsToUpdate.clear();
                        }
                    }
                } catch (Exception e) {
                    log.error("Error ocurred while processing match finish event", e);
                    pendingMatches.clear();
                    initPendingMatches();
                }
            }
            i++;
            log.info("Processing  match finish event is done: " + dto.getMatchId() + ". Progress: " + i + "/" + dtos.length);
        }

        log.info("processMatchFinishEvent is done");
    }

    @Lock(LockType.READ)
    public void processAlignBettorResourcesEvent(AlignBettorResourcesEventDTO dto) {
       Bettor bettor = waitForBettorToBeFreeAndLock(dto.getBettorId());

        try {
            BettingEvent moneyAlignedEvent = createBettingEvent(null, bettor, dto.getAmount().compareTo(BigDecimal.ZERO) <= 0 ? BettingEvent.Type.MONEY_WITHDRAWN : BettingEvent.Type.MONEY_ADDED, null);
            moneyAlignedEvent.setResourcesAvailableBeforeEvent(bettor.getAvailableResources());
            bettor.addToResources(dto.getAmount());
            bettor.addToTotalProfit(dto.getAmount().negate());
            bettor.setAvgResourcesSinceLastAlignment(BigDecimal.ZERO);
            moneyAlignedEvent.setResourcesAvailableAfterEvent(bettor.getAvailableResources());
            bettor.setBettingInProgress(false);
            saveMatchEventResults(Lists.newArrayList(bettor), Lists.newArrayList(moneyAlignedEvent));
        } catch (Exception e) {
            log.error("Error ocurred while money withdrawn event", e);
            bettor.setBettingInProgress(false);
            bettorDao.persist(bettor);
        }

    }


    @Lock(LockType.READ)
    public void processMatchBetProposalEvent(MatchBetProposalDTO dto) {
        Bettor bettor = getBettorNotInProgress(bettorDao.findRefreshedById(dto.getBettorId()));
        Match match = matchDao.findById(dto.getMatchId());
        Type expectedMatchResult = Type.valueOf(dto.getExpectedMatchResult());
        BettingEvent matchBetEvent = createBettingEvent(match, bettor, BettingEvent.Type.MATCH_BET, dto.getProposalSource());

        matchBetEvent.setExpectedMatchResult(expectedMatchResult);
        matchBetEvent.setResourcesAvailableBeforeEvent(bettor.getAvailableResources());
        bettor.subtractFromResources(dto.getRiskAmount());
        matchBetEvent.setResourcesAvailableAfterEvent(bettor.getAvailableResources());
        matchBetEvent.setBetOdd(dto.getOdd());
        bettor.setAvgResourcesSinceLastAlignment(calculateAvgResourcesAlignment(bettor, matchBetEvent));
        bettor.setBettingInProgress(false);

        PendingMatchData pendingMatchData = new PendingMatchData(dto.getRiskAmount(), dto.getOdd(), expectedMatchResult.name(), dto.getBettorId(), dto.getProposalSource());

        try {
            saveMatchEventResults(Lists.newArrayList(bettor), Lists.newArrayList(matchBetEvent));
            betInRealSystem(bettor.getId(), match.getIdentifier(), dto.getRiskAmount(), expectedMatchResult, dto.getOdd());
            addPendingMatchData(pendingMatchData, dto.getMatchId());
        } catch (Exception e) {
            bettor.setBettingInProgress(false);
            bettorDao.persist(bettor);
            if (pendingMatches.containsKey(dto.getMatchId())) {
                pendingMatches.get(dto.getMatchId()).remove(pendingMatchData);
                throw e;
            }
        }
    }

    private void initPendingMatches() {
        List<Object[]> result = bettingEventDao.getPendingBettingEventAndMatchBettorDetails();

        if (!pendingMatches.isEmpty()) {
            throw new IllegalStateException("Initializing pendingMatches when it is not empty!");
        }

        for (Object[] row : result) {
            PendingMatchData pendingMatchData = new PendingMatchData(((BigDecimal) row[3]).subtract((BigDecimal) row[4]), (BigDecimal) row[1], ((MatchOdd.Type) row[2]).name(), (long) row[5], (String) row[6]);
            long matchId = (long) row[0];
            addPendingMatchData(pendingMatchData, matchId);
        }
    }

    private Bettor getBettorNotInProgress(Bettor bettor) {
        Bettor freshBettor = bettor;
        while (!bettorDao.isBettorPendingProgress(freshBettor)) {
            log.info("Waiting for bettor: " + freshBettor.getId() + " to be in progress");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            freshBettor = bettorDao.findRefreshedById(freshBettor.getId());
        }
        return freshBettor;
    }

    private BigDecimal calculateAvgResourcesAlignment(Bettor bettor, BettingEvent event) {
        List<? extends BettingEvent> alignmentEvents = bettingEventDao.getBettorResourcesAlignmentBettingEvents(bettor.getId());
        Long latestAlignEventId = alignmentEvents.stream().map(BettingEvent::getId).max(Long::compare).orElseGet(() -> 0L);
        List<BettingEvent> eventsSinceAlignment = bettor.getBettingEvents().stream().filter(b -> b.getEventType() != BettingEvent.Type.BET_UNSUCCESSFUL)
                .sorted((b1, b2) -> b2.getId().compareTo(b1.getId())).filter(b -> b.getId() > latestAlignEventId).collect(Collectors.toList());
        eventsSinceAlignment.add(event);
        return eventsSinceAlignment.stream().map(BettingEvent::getResourcesAvailableAfterEvent).reduce(BigDecimal::add)
                .get().divide(BigDecimal.valueOf(eventsSinceAlignment.size()), 2, RoundingMode.HALF_UP);
    }

    private void saveMatchEventResults(List<Bettor> bettors, List<BettingEvent> bettingEvents) {
        bettingEventDao.persist(bettingEvents);
        bettorDao.persist(bettors);
    }

    private boolean isBetSuccessful(MatchResult matchResult, Type expectedResult) {
        int homeScore = matchResult.getHomeScore();
        int awayScore = matchResult.getAwayScore();

        switch (expectedResult) {
            case ODD1:
                return homeScore > awayScore;
            case ODDX:
                return homeScore == awayScore;
            case ODD2:
                return homeScore < awayScore;
            case ODD1X:
                return homeScore >= awayScore;
            case ODDX2:
                return homeScore <= awayScore;
            case ODD12:
                return homeScore != awayScore;
            case ODDBTSY:
                return homeScore > 0 && awayScore > 0;
            case ODDBTSN:
                return homeScore == 0 || awayScore == 0;
            case ODDO05:
                return homeScore + awayScore > 0.5;
            case ODDU05:
                return homeScore + awayScore < 0.5;
            case ODDO15:
                return homeScore + awayScore > 1.5;
            case ODDU15:
                return homeScore + awayScore < 1.5;
            case ODDO25:
                return homeScore + awayScore > 2.5;
            case ODDU25:
                return homeScore + awayScore < 2.5;
            case ODDO35:
                return homeScore + awayScore > 3.5;
            case ODDU35:
                return homeScore + awayScore < 3.5;
            case ODDO45:
                return homeScore + awayScore > 4.5;
            case ODDU45:
                return homeScore + awayScore < 4.5;
            case ODDO55:
                return homeScore + awayScore > 5.5;
            case ODDU55:
                return homeScore + awayScore < 5.5;
            case ODDO65:
                return homeScore + awayScore > 6.5;
            case ODDU65:
                return homeScore + awayScore < 6.5;
            default:
                throw new IllegalArgumentException("Cannot handle result: " + expectedResult);
        }

    }

    private void verifyRealBookmakerActions(Bettor bettor) {
        //just make sure that money on bkm account is equal to money for bettor in our db

    }

    private void initBookmaker2RealSystemBinding() {
        // query from database, or some config file, TBD
    }

    private void betInRealSystem(Long bettorId, String matchIdentifier, BigDecimal riskAmount, MatchOdd.Type type, BigDecimal odd) {
        if (bettor2RealSystemBinding.containsKey(bettorId)) {
            bettor2RealSystemBinding.get(bettorId).betMatch(matchIdentifier, riskAmount, type, odd);
        }
    }

    private void addPendingMatchData(PendingMatchData pendingMatchData, Long matchId) {
        pendingMatches.merge(matchId, Sets.newHashSet(pendingMatchData), (s1, s2) -> Sets.newConcurrentHashSet(Sets.union(s1, s2)));
    }

    private BettingEvent createBettingEvent(Match match, Bettor bettor, BettingEvent.Type type, String proposalSource) {
        BettingEvent bettingEvent = new BettingEvent();
        bettingEvent.setEventTime(LocalDateTime.now());
        bettingEvent.setBettor(bettor);
        bettingEvent.setEventType(type);
        bettingEvent.setMatch(match);
        bettingEvent.setProposalSource(proposalSource);
        return bettingEvent;
    }


    static class PendingMatchData {
        private BigDecimal riskAmount;
        private BigDecimal odd;
        private String expectedResult;
        private long bettorId;
        private String proposalSource;


        public PendingMatchData(BigDecimal riskAmount, BigDecimal odd, String expectedResult, long bettorId, String proposalSource) {
            this.riskAmount = riskAmount;
            this.odd = odd;
            this.expectedResult = expectedResult;
            this.bettorId = bettorId;
            this.proposalSource = proposalSource;
        }

        public BigDecimal getRiskAmount() {
            return riskAmount;
        }

        public BigDecimal getOdd() {
            return odd;
        }

        public String getExpectedResult() {
            return expectedResult;
        }

        public long getBettorId() {
            return bettorId;
        }

        public String getProposalSource() {
            return proposalSource;
        }


        @Override
        public String toString() {
            return "PendingMatchData{" +
                    "riskAmount=" + riskAmount +
                    ", odd=" + odd +
                    ", expectedResult=" + expectedResult +
                    ", bettorId=" + bettorId +
                    '}';
        }
    }

}
