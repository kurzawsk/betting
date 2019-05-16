package com.kk.betting.services.bettorhandling.common.service;


import com.kk.betting.domain.*;
import com.kk.betting.dto.MatchBetProposalDTO;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.bettorhandling.common.service.bettorlogic.BettorLogic;
import com.kk.betting.services.common.dao.BettingEventDao;
import com.kk.betting.services.common.dao.BettorDao;
import com.kk.betting.services.common.dao.MatchDao;
import com.kk.betting.services.common.dao.MatchOddDao;
import com.kk.betting.services.common.service.AbstractEntityManagerBasedTest;
import com.kk.betting.services.common.service.ObjectMapperFactory;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Created by KK on 2017-05-07.
 */
public class BettingProposalProcessorTest extends AbstractEntityManagerBasedTest {

    @Spy
    @InjectMocks
    protected BettingEventDao bettingEventDao;

    @Spy
    @InjectMocks
    protected MatchDao matchDao;

    @Spy
    @InjectMocks
    protected MatchOddDao matchOddDao;

    @Spy
    @InjectMocks
    protected BettorDao bettorDao;

    @InjectMocks
    private BettingProposalProcessor bettingProposalProcessor;

    @Test
    public void testAllBettorsAccept() throws IOException {

        startTransaction();
        String proposalSource = "TestSource";
        BigDecimal odd = new BigDecimal("1.5");

        Match match = new Match("Test Match", new Team("t1"), new Team("t2"), LocalDateTime.now().plusDays(2));

        Bookmaker bookmaker = new Bookmaker("test", "test");
        MatchOdd matchOdd = new MatchOdd();
        matchOdd.setOdd1(odd);
        matchOdd.setOddX(odd);
        matchOdd.setOdd2(odd);
        matchOdd.setBookmaker(bookmaker);
        matchOdd.setMatch(match);
        matchOdd.setTimestamp(LocalDateTime.now());

        Bettor bettor1 = new Bettor();
        Bettor bettor2 = new Bettor();
        Bettor bettor3 = new Bettor();

        bettor1.setBookmaker(bookmaker);
        bettor2.setBookmaker(bookmaker);
        bettor3.setBookmaker(bookmaker);

        bettor1.setActive(true);
        bettor2.setActive(true);
        bettor3.setActive(true);

        bettor1.setLogicParameters(ObjectMapperFactory.get().convertValue(new TestBettorLogicAlwaysBet(),Map.class));
        bettor2.setLogicParameters(ObjectMapperFactory.get().convertValue(new TestBettorLogicAlwaysBet(),Map.class));
        bettor3.setLogicParameters(ObjectMapperFactory.get().convertValue(new TestBettorLogicAlwaysBet(),Map.class));

        entityManager.persist(bettor1);
        entityManager.persist(bettor2);
        entityManager.persist(bettor3);
        entityManager.persist(match);
        entityManager.persist(matchOdd);

        MatchBetProposalRawDTO matchBetProposalDTO = MatchBetProposalRawDTO.builder().setMatchId(match.getId()).
                setExpectedMatchResult(MatchOdd.Type.ODD1.name()).setProposalSource(proposalSource).setOdd(odd).build();


        bettingProposalProcessor.init();
        List<MatchBetProposalDTO> acceptedBettingProposals = bettingProposalProcessor.processBettingProposal(matchBetProposalDTO);


        assertEquals(3, acceptedBettingProposals.size());
        assertEquals((long) bettor1.getId(), acceptedBettingProposals.get(0).getBettorId());
        assertEquals((long) bettor2.getId(), acceptedBettingProposals.get(1).getBettorId());
        assertEquals((long) bettor3.getId(), acceptedBettingProposals.get(2).getBettorId());

        assertEquals(TestBettorLogicAlwaysBet.DEFAULT_RISK_AMOUNT, acceptedBettingProposals.get(0).getRiskAmount());
        assertEquals(TestBettorLogicAlwaysBet.DEFAULT_RISK_AMOUNT, acceptedBettingProposals.get(1).getRiskAmount());
        assertEquals(TestBettorLogicAlwaysBet.DEFAULT_RISK_AMOUNT, acceptedBettingProposals.get(2).getRiskAmount());

        acceptedBettingProposals.stream().forEach(abp -> {
            assertEquals(MatchOdd.Type.ODD1.name(), abp.getExpectedMatchResult());
            assertEquals(proposalSource, abp.getProposalSource());
            assertEquals((long) match.getId(), abp.getMatchId());
            assertEquals(odd, abp.getOdd());
        });

        rollbackTransaction();
    }


    @Test
    public void testOnlyChosenBettorsAccept() throws IOException {
        startTransaction();
        BigDecimal odd = new BigDecimal("2.0");
        String proposalSource = "TestSource";

        Bookmaker bookmaker = new Bookmaker("test", "test");
        Match match = new Match("Test Match", new Team("t1"), new Team("t2"), LocalDateTime.now().plusDays(2));
        MatchOdd matchOdd = new MatchOdd();
        matchOdd.setOdd1(odd);
        matchOdd.setOddX(odd);
        matchOdd.setOdd2(odd);
        matchOdd.setOdd1X(odd);
        matchOdd.setBookmaker(bookmaker);
        matchOdd.setMatch(match);
        matchOdd.setTimestamp(LocalDateTime.now());

        Bettor bettor1 = new Bettor();
        bettor1.setLogicParameters(ObjectMapperFactory.get().convertValue(new TestBettorLogicAlwaysEmpty(),Map.class));

        Bettor bettor2 = new Bettor();
        bettor2.setLogicParameters(ObjectMapperFactory.get().convertValue(new TestBettorLogicBetIfOddGreaterThan1_5(),Map.class));

        Bettor bettor3 = new Bettor();
        bettor3.setLogicParameters(ObjectMapperFactory.get().convertValue(new TestBettorLogicBetIfOddLE1_5(),Map.class));

        bettor1.setBookmaker(bookmaker);
        bettor2.setBookmaker(bookmaker);
        bettor3.setBookmaker(bookmaker);

        bettor1.setActive(true);
        bettor2.setActive(true);
        bettor3.setActive(true);

        entityManager.persist(bettor1);
        entityManager.persist(bettor2);
        entityManager.persist(bettor3);
        entityManager.persist(match);
        entityManager.persist(matchOdd);

        MatchBetProposalRawDTO matchBetProposalRawDTO = MatchBetProposalRawDTO.builder().setMatchId(match.getId()).
                setExpectedMatchResult(MatchOdd.Type.ODD1X.name()).setProposalSource(proposalSource).setOdd(odd).build();

        bettingProposalProcessor.init();
        List<MatchBetProposalDTO> acceptedBettingProposals = bettingProposalProcessor.processBettingProposal(matchBetProposalRawDTO);


        assertEquals(1, acceptedBettingProposals.size());
        assertEquals((long) bettor2.getId(), acceptedBettingProposals.get(0).getBettorId());

        assertEquals(MatchOdd.Type.ODD1X.name(), acceptedBettingProposals.get(0).getExpectedMatchResult());
        assertEquals(proposalSource, acceptedBettingProposals.get(0).getProposalSource());
        assertEquals((long) match.getId(), acceptedBettingProposals.get(0).getMatchId());
        assertEquals(odd, acceptedBettingProposals.get(0).getOdd());

        rollbackTransaction();
    }

    @Test
    public void testOddAlignment() throws IOException {
        startTransaction();
        String proposalSource = "TestSource";
        BigDecimal odd1 = new BigDecimal("1.5");
        BigDecimal odd2 = new BigDecimal("1.8");
        BigDecimal odd3 = new BigDecimal("2.1");

        Match match = new Match("Test Match", new Team("t1"), new Team("t2"), LocalDateTime.now().plusDays(2));

        Bookmaker bookmaker = new Bookmaker("test", "test");
        MatchOdd matchOdd1 = new MatchOdd();
        matchOdd1.setOdd1(odd1);
        matchOdd1.setOddX(odd1);
        matchOdd1.setOdd2(odd1);
        matchOdd1.setBookmaker(bookmaker);
        matchOdd1.setMatch(match);
        matchOdd1.setTimestamp(LocalDateTime.now().minusMinutes(14));

        MatchOdd matchOdd2 = new MatchOdd();
        matchOdd2.setOdd1(odd2);
        matchOdd2.setOddX(odd2);
        matchOdd2.setOdd2(odd2);
        matchOdd2.setBookmaker(bookmaker);
        matchOdd2.setMatch(match);
        matchOdd2.setTimestamp(LocalDateTime.now().minusMinutes(15));

        MatchOdd matchOdd3 = new MatchOdd();
        matchOdd3.setOdd1(odd3);
        matchOdd3.setOddX(odd3);
        matchOdd3.setOdd2(odd3);
        matchOdd3.setBookmaker(bookmaker);
        matchOdd3.setMatch(match);
        matchOdd3.setTimestamp(LocalDateTime.now().minusMinutes(20));

        Bettor bettor1 = new Bettor();
        bettor1.setBookmaker(bookmaker);
        bettor1.setActive(true);
        bettor1.setLogicParameters(ObjectMapperFactory.get().convertValue(new TestBettorLogicAlwaysBet(),Map.class));


        entityManager.persist(bettor1);

        entityManager.persist(match);
        entityManager.persist(matchOdd1);
        entityManager.persist(matchOdd2);
        entityManager.persist(matchOdd3);
        MatchBetProposalRawDTO matchBetProposalDTO = MatchBetProposalRawDTO.builder().setMatchId(match.getId()).
                setExpectedMatchResult(MatchOdd.Type.ODD1.name()).setProposalSource(proposalSource).setOdd(odd2).build();


        bettingProposalProcessor.init();
        List<MatchBetProposalDTO> acceptedBettingProposals = bettingProposalProcessor.processBettingProposal(matchBetProposalDTO);


        assertEquals(1, acceptedBettingProposals.size());
        assertEquals((long) bettor1.getId(), acceptedBettingProposals.get(0).getBettorId());
        assertEquals(TestBettorLogicAlwaysBet.DEFAULT_RISK_AMOUNT, acceptedBettingProposals.get(0).getRiskAmount());
        assertEquals(MatchOdd.Type.ODD1.name(), acceptedBettingProposals.get(0).getExpectedMatchResult());
        assertEquals(proposalSource, acceptedBettingProposals.get(0).getProposalSource());
        assertEquals((long) match.getId(), acceptedBettingProposals.get(0).getMatchId());
        assertEquals(odd1, acceptedBettingProposals.get(0).getOdd());


        rollbackTransaction();
    }


    public static class TestBettorLogicAlwaysBet implements BettorLogic {

        public static final BigDecimal DEFAULT_RISK_AMOUNT = new BigDecimal("10.00");

        @Override
        public Optional<MatchBetProposalDTO> processBetProposal(MatchBetProposalRawDTO matchBetProposalRawDTO, Bettor bettor) {
            return Optional.of(MatchBetProposalDTO.builder()
                    .setMatchId(matchBetProposalRawDTO.getMatchId())
                    .setBettorId(bettor.getId())
                    .setOdd(matchBetProposalRawDTO.getOdd())
                    .setExpectedMatchResult(matchBetProposalRawDTO.getExpectedMatchResult())
                    .setProposalSource(matchBetProposalRawDTO.getProposalSource())
                    .setRiskAmount(DEFAULT_RISK_AMOUNT).build());

        }
    }


    public static class TestBettorLogicAlwaysEmpty implements BettorLogic {

        @Override
        public Optional<MatchBetProposalDTO> processBetProposal(MatchBetProposalRawDTO matchBetProposalRawDTO, Bettor bettor) {
            return Optional.empty();
        }
    }


    public static class TestBettorLogicBetIfOddGreaterThan1_5 implements BettorLogic {

        @Override
        public Optional<MatchBetProposalDTO> processBetProposal(MatchBetProposalRawDTO matchBetProposalRawDTO, Bettor bettor) {
            return matchBetProposalRawDTO.getOdd().doubleValue() <= 1.5 ? Optional.empty() : Optional.of(MatchBetProposalDTO.builder()
                    .setMatchId(matchBetProposalRawDTO.getMatchId())
                    .setBettorId(bettor.getId())
                    .setOdd(matchBetProposalRawDTO.getOdd())
                    .setExpectedMatchResult(matchBetProposalRawDTO.getExpectedMatchResult())
                    .setProposalSource(matchBetProposalRawDTO.getProposalSource())
                    .setRiskAmount(new BigDecimal("10.00")).build());

        }
    }

    public static class TestBettorLogicBetIfOddLE1_5 implements BettorLogic {

        @Override
        public Optional<MatchBetProposalDTO> processBetProposal(MatchBetProposalRawDTO matchBetProposalRawDTO, Bettor bettor) {
            return matchBetProposalRawDTO.getOdd().doubleValue() > 1.5 ? Optional.empty() : Optional.of(MatchBetProposalDTO.builder()
                    .setMatchId(matchBetProposalRawDTO.getMatchId())
                    .setBettorId(bettor.getId())
                    .setOdd(matchBetProposalRawDTO.getOdd())
                    .setExpectedMatchResult(matchBetProposalRawDTO.getExpectedMatchResult())
                    .setProposalSource(matchBetProposalRawDTO.getProposalSource())
                    .setRiskAmount(new BigDecimal("10.00")).build());

        }
    }

}
