package com.kk.betting.services.bettorhandling.common.service;

import com.google.common.collect.Sets;
import com.kk.betting.domain.*;
import com.kk.betting.dto.MatchBetProposalDTO;
import com.kk.betting.dto.MatchFinishEventDTO;
import com.kk.betting.services.common.dao.BettingEventDao;
import com.kk.betting.services.common.dao.BettorDao;
import com.kk.betting.services.common.dao.MatchDao;
import com.kk.betting.services.common.service.AbstractEntityManagerBasedTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by KK on 2017-02-05.
 */
public class BettingMatchEventProcessorTest extends AbstractEntityManagerBasedTest {

    @Spy
    @InjectMocks
    private BettingEventDao bettingEventDao;

    @Spy
    @InjectMocks
    private MatchDao matchDao;

    @Spy
    @InjectMocks
    private BettorDao bettorDao;

    @InjectMocks
    private BettingMatchEventProcessor bettingMatchEventProcessor = new BettingMatchEventProcessor();

    @Test
    public void testInitPendingMatches() {
        startTransaction();

        Match match1 = new Match("testMatch_1", new Team("testTeam_0"), new Team("testTeam_1"),
                LocalDateTime.now());
        Match match2 = new Match("testMatch_2", new Team("testTeam_2"), new Team("testTeam_3"),
                LocalDateTime.now());
        Match match3 = new Match("testMatch_3", new Team("testTeam_4"), new Team("testTeam_5"),
                LocalDateTime.now());
        Match match4 = new Match("testMatch_4", new Team("testTeam_6"), new Team("testTeam_7"),
                LocalDateTime.now());

        Bookmaker bookmaker = new Bookmaker("test", "test");

        Bettor bettor1 = new Bettor();
        Bettor bettor2 = new Bettor();

        bettor1.setActive(true);
        bettor1.setBookmaker(bookmaker);
        bettor1.addToResources(new BigDecimal(100.00));

        bettor2.setActive(true);
        bettor2.setBookmaker(bookmaker);
        bettor2.addToResources(new BigDecimal(50.00));

        BettingEvent match1Bet = new BettingEvent();
        match1Bet.setBettor(bettor1);
        match1Bet.setMatch(match1);
        match1Bet.setEventType(BettingEvent.Type.MATCH_BET);
        match1Bet.setBetOdd(new BigDecimal(1.5));
        match1Bet.setResourcesAvailableBeforeEvent(bettor1.getAvailableResources());
        bettor1.subtractFromResources(new BigDecimal(10));
        match1Bet.setResourcesAvailableAfterEvent(bettor1.getAvailableResources());
        match1Bet.setEventTime(LocalDateTime.now());
        match1Bet.setExpectedMatchResult(MatchOdd.Type.ODD1);


        BettingEvent match3Bet = new BettingEvent();
        match3Bet.setBettor(bettor1);
        match3Bet.setMatch(match3);
        match3Bet.setEventType(BettingEvent.Type.MATCH_BET);
        match3Bet.setBetOdd(new BigDecimal(1.8));
        match3Bet.setResourcesAvailableBeforeEvent(bettor1.getAvailableResources());
        bettor1.subtractFromResources(new BigDecimal(20.00));
        match3Bet.setResourcesAvailableAfterEvent(bettor1.getAvailableResources());
        match3Bet.setEventTime(LocalDateTime.now());
        match3Bet.setExpectedMatchResult(MatchOdd.Type.ODD1);


        BettingEvent match4BetBettor1 = new BettingEvent();
        match4BetBettor1.setBettor(bettor1);
        match4BetBettor1.setMatch(match4);
        match4BetBettor1.setEventType(BettingEvent.Type.MATCH_BET);
        match4BetBettor1.setBetOdd(new BigDecimal(1.3));
        match4BetBettor1.setResourcesAvailableBeforeEvent(bettor1.getAvailableResources());
        bettor1.subtractFromResources(new BigDecimal(20.00));
        match4BetBettor1.setResourcesAvailableAfterEvent(bettor1.getAvailableResources());
        match4BetBettor1.setEventTime(LocalDateTime.now());
        match4BetBettor1.setExpectedMatchResult(MatchOdd.Type.ODD1);

        BettingEvent match4BetBettor2 = new BettingEvent();
        match4BetBettor2.setBettor(bettor2);
        match4BetBettor2.setMatch(match4);
        match4BetBettor2.setEventType(BettingEvent.Type.MATCH_BET);
        match4BetBettor2.setBetOdd(new BigDecimal(1.35));
        match4BetBettor2.setResourcesAvailableBeforeEvent(bettor1.getAvailableResources());
        bettor1.subtractFromResources(new BigDecimal(23.50));
        match4BetBettor2.setResourcesAvailableAfterEvent(bettor1.getAvailableResources());
        match4BetBettor2.setEventTime(LocalDateTime.now());
        match4BetBettor2.setExpectedMatchResult(MatchOdd.Type.ODDX);

        entityManager.persist(match1);
        entityManager.persist(match2);
        entityManager.persist(match3);
        entityManager.persist(match4);
        entityManager.persist(bookmaker);
        entityManager.persist(bettor1);
        entityManager.persist(bettor2);
        entityManager.persist(match1Bet);
        entityManager.persist(match3Bet);
        entityManager.persist(match4BetBettor1);
        entityManager.persist(match4BetBettor2);

        //match3 was not successfully bet
        match3.setResult(MatchResult.fromScores(0, 0));
        BettingEvent match3Finished = new BettingEvent();
        match3Finished.setBettor(bettor1);
        match3Finished.setMatch(match3);
        match3Finished.setEventType(BettingEvent.Type.BET_UNSUCCESSFUL);
        match3Finished.setResourcesAvailableBeforeEvent(bettor1.getAvailableResources());
        match3Finished.setResourcesAvailableAfterEvent(bettor1.getAvailableResources());
        match3Finished.setEventTime(LocalDateTime.now());

        entityManager.persist(match3Finished);
        entityManager.persist(match3);

        long match1Id = match1.getId();
        long match4Id = match4.getId();

        bettingMatchEventProcessor.init();

        assertEquals("Different number of obtained pending matches ", bettingMatchEventProcessor.pendingMatches.keySet(), Sets.newHashSet(match1Id, match4Id));
        assertEquals("Different number of obtained pending bettings for match: " + match1Id, bettingMatchEventProcessor.pendingMatches.get(match1Id).size(), 1);
        assertEquals("Different number of obtained pending bettings for match: " + match4Id, bettingMatchEventProcessor.pendingMatches.get(match4Id).size(), 2);

        rollbackTransaction();
    }

    @Test
    public void testProcessMatchFinishEventMatchSuccess() {
        startTransaction();

        Match match1 = new Match("testMatch_1", new Team("testTeam_0"), new Team("testTeam_1"),
                LocalDateTime.now());

        Bookmaker bookmaker = new Bookmaker("test", "test");

        Bettor bettor1 = new Bettor();
        //bettor1.setLogicClass(TestBettorLogic.class.);
        bettor1.setActive(true);
        bettor1.setBookmaker(bookmaker);
        bettor1.addToResources(new BigDecimal(100.00));

        BigDecimal riskAmount = new BigDecimal(10.00);

        BettingEvent match1Bet = new BettingEvent();
        match1Bet.setBettor(bettor1);
        match1Bet.setMatch(match1);
        match1Bet.setEventType(BettingEvent.Type.MATCH_BET);
        match1Bet.setBetOdd(new BigDecimal(1.5));
        match1Bet.setResourcesAvailableBeforeEvent(bettor1.getAvailableResources());
        bettor1.subtractFromResources(riskAmount);
        match1Bet.setResourcesAvailableAfterEvent(bettor1.getAvailableResources());
        match1Bet.setEventTime(LocalDateTime.now());
        match1Bet.setExpectedMatchResult(MatchOdd.Type.ODD1);

        entityManager.persist(match1);
        entityManager.persist(bookmaker);
        entityManager.persist(bettor1);
        entityManager.persist(match1Bet);
        MatchFinishEventDTO matchFinishEventDTO = new MatchFinishEventDTO(match1.getId(), "2:1");
        BettingMatchEventProcessor.PendingMatchData pendingMatchData = new BettingMatchEventProcessor.PendingMatchData(riskAmount, match1Bet.getBetOdd(), MatchOdd.Type.ODD1.name(), bettor1.getId(), "");


        commitTransaction();
        startTransaction();

        bettingMatchEventProcessor.pendingMatches.put(match1.getId(), Sets.newHashSet(pendingMatchData));
        bettingMatchEventProcessor.processMatchFinishEvent(new MatchFinishEventDTO[]{matchFinishEventDTO});

        Bettor freshBettor = entityManager.find(Bettor.class, bettor1.getId());
        assertEquals("Expected resources different from obtained", freshBettor.getAvailableResources().setScale(2), new BigDecimal(105.00).setScale(2));

        rollbackTransaction();
    }


    public void testProcessMatchFinishEventMatchNotSuccess() {
        startTransaction();

        Match match1 = new Match("testMatch_1", new Team("testTeam_0"), new Team("testTeam_1"),
                LocalDateTime.now());

        Bookmaker bookmaker = new Bookmaker("test", "test");

        Bettor bettor1 = new Bettor();
        bettor1.setActive(true);
        bettor1.setBookmaker(bookmaker);
        bettor1.addToResources(new BigDecimal(100.00));

        BigDecimal riskAmount = new BigDecimal(10.00);

        BettingEvent match1Bet = new BettingEvent();
        match1Bet.setBettor(bettor1);
        match1Bet.setMatch(match1);
        match1Bet.setEventType(BettingEvent.Type.MATCH_BET);
        match1Bet.setBetOdd(new BigDecimal(1.5));
        match1Bet.setResourcesAvailableBeforeEvent(bettor1.getAvailableResources());
        bettor1.subtractFromResources(riskAmount);
        match1Bet.setResourcesAvailableAfterEvent(bettor1.getAvailableResources());
        match1Bet.setEventTime(LocalDateTime.now());
        match1Bet.setExpectedMatchResult(MatchOdd.Type.ODD2);

        entityManager.persist(match1);
        entityManager.persist(bookmaker);
        entityManager.persist(bettor1);
        entityManager.persist(match1Bet);

        BettingMatchEventProcessor.PendingMatchData pendingMatchData = new BettingMatchEventProcessor.PendingMatchData(riskAmount, match1Bet.getBetOdd(), MatchOdd.Type.ODD2.name(), bettor1.getId(), "");
        MatchFinishEventDTO matchFinishEventDTO = new MatchFinishEventDTO(match1.getId(), "3:3");

        bettingMatchEventProcessor.pendingMatches.put(match1.getId(), Sets.newHashSet(pendingMatchData));
        bettingMatchEventProcessor.processMatchFinishEvent(new MatchFinishEventDTO[]{matchFinishEventDTO});

        Bettor freshBettor = entityManager.find(Bettor.class, bettor1.getId());

        assertEquals("Expected resources different from obtained", freshBettor.getAvailableResources().setScale(2), new BigDecimal(90.00).setScale(2));

        rollbackTransaction();
    }

    @Test
    public void testProcessMatchFinishEventMatchWithdrawn() {
        startTransaction();

        Match match1 = new Match("testMatch_1", new Team("testTeam_0"), new Team("testTeam_1"),
                LocalDateTime.now());

        Bookmaker bookmaker = new Bookmaker("test", "test");

        Bettor bettor1 = new Bettor();
        bettor1.setActive(true);
        bettor1.setBookmaker(bookmaker);
        bettor1.addToResources(new BigDecimal(100.00));

        BigDecimal riskAmount = new BigDecimal(10.00);

        BettingEvent match1Bet = new BettingEvent();
        match1Bet.setBettor(bettor1);
        match1Bet.setMatch(match1);
        match1Bet.setEventType(BettingEvent.Type.MATCH_BET);
        match1Bet.setBetOdd(new BigDecimal(1.5));
        match1Bet.setResourcesAvailableBeforeEvent(bettor1.getAvailableResources());
        bettor1.subtractFromResources(riskAmount);
        match1Bet.setResourcesAvailableAfterEvent(bettor1.getAvailableResources());
        match1Bet.setEventTime(LocalDateTime.now());
        match1Bet.setExpectedMatchResult(MatchOdd.Type.ODD1X);

        entityManager.persist(match1);
        entityManager.persist(bookmaker);
        entityManager.persist(bettor1);
        entityManager.persist(match1Bet);
        BettingMatchEventProcessor.PendingMatchData pendingMatchData = new BettingMatchEventProcessor.PendingMatchData(riskAmount, match1Bet.getBetOdd(), MatchOdd.Type.ODD1X.name(), bettor1.getId(), "");
        MatchFinishEventDTO matchFinishEventDTO = new MatchFinishEventDTO(match1.getId(), MatchResult.Type.CANCELLED.getCode() + ":" + MatchResult.Type.CANCELLED.getCode());

        commitTransaction();
        startTransaction();

        bettingMatchEventProcessor.pendingMatches.put(match1.getId(), Sets.newHashSet(pendingMatchData));
        bettingMatchEventProcessor.processMatchFinishEvent(new MatchFinishEventDTO[]{matchFinishEventDTO});

        Bettor freshBettor = entityManager.find(Bettor.class, bettor1.getId());
        assertEquals("Expected resources different from obtained", freshBettor.getAvailableResources().setScale(2), new BigDecimal(100.00).setScale(2));

        rollbackTransaction();
    }

    @Test
    public void testProcessMatchBetProposalEvent() {
        startTransaction();

        Match match = new Match("testMatch_1", new Team("testTeam_0"), new Team("testTeam_1"),
                LocalDateTime.now());

        Bookmaker bookmaker = new Bookmaker("test", "test");

        Bettor bettor = new Bettor();
        bettor.setActive(true);
        bettor.setBookmaker(bookmaker);
        bettor.addToResources(new BigDecimal(100.00));
        bettor.setBettingInProgress(true);

        BigDecimal riskAmount = new BigDecimal(10.00);
        BigDecimal odd = new BigDecimal(2.01);

        entityManager.persist(match);
        entityManager.persist(bookmaker);
        entityManager.persist(bettor);

        commitTransaction();

        startTransaction();

        MatchBetProposalDTO dto = MatchBetProposalDTO.builder().setMatchId(match.getId()).setBettorId(bettor.getId()).setExpectedMatchResult(MatchOdd.Type.ODDX.name()).setRiskAmount(riskAmount).setOdd(odd).build();

        bettingMatchEventProcessor.processMatchBetProposalEvent(dto);

        BettingEvent bettingEvent = entityManager.createQuery("from BettingEvent", BettingEvent.class).getResultList().get(0);
        assertEquals(match, bettingEvent.getMatch());
        assertEquals(bettor, bettingEvent.getBettor());
        assertEquals(new BigDecimal(100.00), bettingEvent.getResourcesAvailableBeforeEvent());
        assertEquals(new BigDecimal(90.00), bettingEvent.getResourcesAvailableAfterEvent());
        assertEquals(MatchOdd.Type.ODDX, bettingEvent.getExpectedMatchResult());
        assertEquals(BettingEvent.Type.MATCH_BET, bettingEvent.getType());

        assertEquals(1, bettingMatchEventProcessor.pendingMatches.get(match.getId()).size());
        assertTrue(bettor.getId() == bettingMatchEventProcessor.pendingMatches.get(match.getId()).iterator().next().getBettorId());
        assertEquals(MatchOdd.Type.ODDX.name(), bettingMatchEventProcessor.pendingMatches.get(match.getId()).iterator().next().getExpectedResult());
        assertEquals(riskAmount, bettingMatchEventProcessor.pendingMatches.get(match.getId()).iterator().next().getRiskAmount());
        assertEquals(odd, bettingMatchEventProcessor.pendingMatches.get(match.getId()).iterator().next().getOdd());

        rollbackTransaction();

    }


//    public static class TestBettor extends Bettor {
//
//        public TestBettor(){
//            b
//        }
//
//    }


//    public static class TestBettorLogic implements BettorLogic{
//
//        @Override
//        public void init(Map<String, Object> parameters) {
//
//        }
//
//        @Override
//        public Map<String, Object> asParameters() {
//            return null;
//        }
//
//        @Override
//        public Optional<MatchBetProposalDTO> processBetProposal(MatchBetProposalRawDTO matchBetProposalRawDTO, Bettor bettor) {
//            return Optional.empty();
//        }
//    }

}
