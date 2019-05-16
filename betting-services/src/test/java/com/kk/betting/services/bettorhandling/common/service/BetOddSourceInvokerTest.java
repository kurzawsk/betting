package com.kk.betting.services.bettorhandling.common.service;

import com.kk.betting.services.common.dao.*;
import com.kk.betting.services.common.matcher.service.MappingService;
import com.kk.betting.services.common.service.AbstractEntityManagerBasedTest;
import com.kk.betting.services.common.service.WebPageBrowser;
import com.kk.betting.domain.*;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.common.util.TestUtil;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by KK on 2017-05-21.
 */
@Ignore("We need to mock up cdi behaviour")
public class BetOddSourceInvokerTest extends AbstractEntityManagerBasedTest {

    @Mock
    private RawBettingProposalSender messageSender;

    @Mock
    private WebPageBrowser webPageBrowser;

    @Spy
    @InjectMocks
    private MatchDao matchDao;

    @Spy
    @InjectMocks
    private MatchOddDao matchOddDao;

    @Spy
    @InjectMocks
    private BookmakerDao bookmakerDao;

    @Spy
    @InjectMocks
    private BettingProposalSourceDao bettingProposalSourceDao;

    @Spy
    @InjectMocks
    private BettingProposalSourceMatchDao bettingProposalSourceMatchDao;

    @Spy
    @InjectMocks
    private MappingService mappingService;

    @InjectMocks
    private BetOddSourceInvoker betOddSourceInvoker = new BetOddSourceInvoker();


    @Test
    public void testProposeSingleMatch() {
        startTransaction();
        TestUtil.generateBookmakers(entityManager);

        BettingProposalSource bettingProposalSource = new BettingProposalSource();
        bettingProposalSource.setLogicImpClass(TestBettingProposalSourceLogic.class.getName());
        bettingProposalSource.setScheduleExpression("HOUR=*");
        bettingProposalSource.setActive(true);

        Match match = new Match("Test match", new Team("team1"), new Team("team2"), LocalDateTime.now().plusDays(2));
        entityManager.persist(match);
        entityManager.persist(bettingProposalSource);

        betOddSourceInvoker.init();
        List<MatchBetProposalRawDTO> result = betOddSourceInvoker.invokeBetOddSourceLogic(bettingProposalSource);
        assertEquals(1, result.size());

        rollbackTransaction();
    }

    @Test
    public void testFilteringAlreadyBetMatches() {
        startTransaction();
        TestUtil.generateBookmakers(entityManager);

        BettingProposalSource bettingProposalSource = new BettingProposalSource();
        bettingProposalSource.setLogicImpClass(TestBettingProposalSourceLogic.class.getName());
        bettingProposalSource.setScheduleExpression("HOUR=*");
        bettingProposalSource.setActive(true);

        Match match1 = new Match("Test match1", new Team("team1"), new Team("team2"), LocalDateTime.now().plusDays(2));
        Match match2 = new Match("Test match2", new Team("team3"), new Team("team4"), LocalDateTime.now().minusHours(5));
        Match match3 = new Match("Test match3", new Team("team5"), new Team("team6"), LocalDateTime.now().plusDays(1));
        Match match4 = new Match("Test match4", new Team("team7"), new Team("team8"), LocalDateTime.now().plusDays(1));
        BettingProposalSourceMatch bettingProposalSourceMatch = new BettingProposalSourceMatch(match3, bettingProposalSource);

        entityManager.persist(match1);
        entityManager.persist(match2);
        entityManager.persist(match3);
        entityManager.persist(match4);
        entityManager.persist(bettingProposalSource);
        entityManager.persist(bettingProposalSourceMatch);

        betOddSourceInvoker.init();
        List<MatchBetProposalRawDTO> result = betOddSourceInvoker.invokeBetOddSourceLogic(bettingProposalSource);

        List<Long> proposedMatchIds = result.stream().map(MatchBetProposalRawDTO::getMatchId).collect(Collectors.toList());
        assertEquals(2, proposedMatchIds.size());

        assertThat(proposedMatchIds,
                IsIterableContainingInAnyOrder.containsInAnyOrder(match1.getId(), match4.getId()));


        rollbackTransaction();
    }


    @Test
    public void testFilteringAlreadyBetMatchesMultipleTimes() {
        startTransaction();
        TestUtil.generateBookmakers(entityManager);

        BettingProposalSource bettingProposalSource = new BettingProposalSource();
        bettingProposalSource.setLogicImpClass(TestBettingProposalSourceLogic.class.getName());
        bettingProposalSource.setScheduleExpression("HOUR=*");
        bettingProposalSource.setActive(true);

        Match match1 = new Match("Test match1", new Team("team1"), new Team("team2"), LocalDateTime.now().plusDays(2));
        Match match2 = new Match("Test match2", new Team("team3"), new Team("team4"), LocalDateTime.now().minusHours(5));
        Match match3 = new Match("Test match3", new Team("team5"), new Team("team6"), LocalDateTime.now().plusDays(1));
        Match match4 = new Match("Test match4", new Team("team7"), new Team("team8"), LocalDateTime.now().plusDays(1));
        BettingProposalSourceMatch bettingProposalSourceMatch = new BettingProposalSourceMatch(match3, bettingProposalSource);

        entityManager.persist(match1);
        entityManager.persist(match2);
        entityManager.persist(match3);
        entityManager.persist(match4);
        entityManager.persist(bettingProposalSource);
        entityManager.persist(bettingProposalSourceMatch);

        betOddSourceInvoker.init();
        betOddSourceInvoker.invokeBetOddSourceLogic(bettingProposalSource);

        List<MatchBetProposalRawDTO> result = betOddSourceInvoker.invokeBetOddSourceLogic(bettingProposalSource);
        List<Long> proposedMatchIds = result.stream().map(MatchBetProposalRawDTO::getMatchId).collect(Collectors.toList());
        assertEquals(0, proposedMatchIds.size());

        rollbackTransaction();
    }


    @Test
    public void testFilteringAlreadyBetMatchesMultipleBS() {
        startTransaction();
        TestUtil.generateBookmakers(entityManager);

        BettingProposalSource bettingProposalSource1 = new BettingProposalSource();
        bettingProposalSource1.setLogicImpClass(TestBettingProposalSourceLogic.class.getName());
        bettingProposalSource1.setScheduleExpression("HOUR=*");
        bettingProposalSource1.setActive(true);

        BettingProposalSource bettingProposalSource2 = new BettingProposalSource();
        bettingProposalSource2.setLogicImpClass(TestBettingProposalSourceLogic.class.getName());
        bettingProposalSource2.setScheduleExpression("HOUR=1;DAY_OF_MONTH=*");
        bettingProposalSource2.setActive(true);

        Match match1 = new Match("Test match1", new Team("team1"), new Team("team2"), LocalDateTime.now().plusDays(2));
        Match match2 = new Match("Test match2", new Team("team3"), new Team("team4"), LocalDateTime.now().minusHours(5));
        Match match3 = new Match("Test match3", new Team("team5"), new Team("team6"), LocalDateTime.now().plusDays(1));
        Match match4 = new Match("Test match4", new Team("team7"), new Team("team8"), LocalDateTime.now().plusDays(1));
        BettingProposalSourceMatch bettingProposalSourceMatch1 = new BettingProposalSourceMatch(match3, bettingProposalSource1);
        BettingProposalSourceMatch bettingProposalSourceMatch2 = new BettingProposalSourceMatch(match3, bettingProposalSource2);
        BettingProposalSourceMatch bettingProposalSourceMatch3 = new BettingProposalSourceMatch(match4, bettingProposalSource2);

        entityManager.persist(match1);
        entityManager.persist(match2);
        entityManager.persist(match3);
        entityManager.persist(match4);
        entityManager.persist(bettingProposalSource1);
        entityManager.persist(bettingProposalSource2);

        entityManager.persist(bettingProposalSourceMatch1);
        entityManager.persist(bettingProposalSourceMatch2);
        entityManager.persist(bettingProposalSourceMatch3);


        betOddSourceInvoker.init();
        List<MatchBetProposalRawDTO> result1 = betOddSourceInvoker.invokeBetOddSourceLogic(bettingProposalSource1);
        List<MatchBetProposalRawDTO> result2 = betOddSourceInvoker.invokeBetOddSourceLogic(bettingProposalSource2);


        List<Long> proposedMatchIds1 = result1.stream().map(MatchBetProposalRawDTO::getMatchId).collect(Collectors.toList());
        List<Long> proposedMatchIds2 = result2.stream().map(MatchBetProposalRawDTO::getMatchId).collect(Collectors.toList());
        assertEquals(2, proposedMatchIds1.size());
        assertEquals(1, proposedMatchIds2.size());

        assertThat(proposedMatchIds1,
                IsIterableContainingInAnyOrder.containsInAnyOrder(match1.getId(), match4.getId()));

        assertThat(proposedMatchIds2,
                IsIterableContainingInAnyOrder.containsInAnyOrder(match1.getId()));

        rollbackTransaction();
    }

    public static class TestBettingProposalSourceLogic extends BettingProposalSourceLogic {

        @Override
        protected List<MatchBetProposalRawDTO> prepareBettingProposals() {
            List<? extends Match> matches = matchDao.getMatchesStartingBetween(LocalDateTime.now(), LocalDateTime.now().plusDays(30));
            return matches.stream().filter(m -> m.getStartTime().isAfter(LocalDateTime.now())).map(match ->
                    MatchBetProposalRawDTO.builder().setOdd(new BigDecimal("1.5"))
                            .setProposalSource(getClass().getName())
                            .setExpectedMatchResult(MatchOdd.Type.ODD1.toString())
                            .setMatchId(match.getId()).build()).collect(Collectors.toList());
        }




    }
}
