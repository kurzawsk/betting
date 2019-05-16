package com.kk.betting.services.bettorhandling.common.service.bettorlogic;

import com.google.common.collect.ImmutableSet;
import com.kk.betting.domain.Bettor;
import com.kk.betting.domain.MatchOdd;
import com.kk.betting.dto.MatchBetProposalDTO;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.bettorhandling.common.service.riskamountselection.RiskAmountProvider;
import com.kk.betting.services.common.service.ObjectMapperFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.doReturn;


/**
 * Created by KK on 2017-08-06.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RiskAmountProvider.class, SupportedOddRangesProvider.class})
public class ParametrizedBettorLogicTest {

    private RiskAmountProvider riskAmountProvider;
    private SupportedOddRangesProvider supportedOddRangesProvider;
    private EnumSet<MatchOdd.Type> supportedOddTypes;
    private Set<String> supportedProposalsSources;

    private ParametrizedBettorLogic parametrizedBettorLogic = new ParametrizedBettorLogic();

    @Test
    public void betProposalAccepted() {
        // Arrange
        supportedOddTypes = EnumSet.of(MatchOdd.Type.ODD1);
        supportedProposalsSources = ImmutableSet.of("TEST1", "TEST2");
        init();
        BigDecimal riskAmount = new BigDecimal("20.00");
        doReturn(Optional.of(riskAmount)).when(riskAmountProvider).get(any(BigDecimal.class), any(BigDecimal.class));
        doReturn(true).when(supportedOddRangesProvider).isInRange(any(BigDecimal.class));

        long bettorId = 1L;
        long matchId = 1L;
        BigDecimal odd = new BigDecimal("1.50");
        MatchBetProposalRawDTO matchBetProposalRawDTO = MatchBetProposalRawDTO.builder().setProposalSource("TEST1")
                .setExpectedMatchResult("ODD1").setOdd(odd).setMatchId(matchId).build();
        Bettor bettor = new Bettor();
        bettor.setId(bettorId);
        bettor.addToResources(new BigDecimal("200.00"));

        //Act
        Optional<MatchBetProposalDTO> result = parametrizedBettorLogic.processBetProposal(matchBetProposalRawDTO, bettor);

        //Assert
        assertEquals(true, result.isPresent());
        assertEquals(bettorId, result.get().getBettorId());
        assertEquals(matchId, result.get().getMatchId());
        assertEquals(odd, result.get().getOdd());
        assertEquals(riskAmount, result.get().getRiskAmount());
        assertEquals("TEST1", result.get().getProposalSource());


    }

    @Test
    public void betProposalAcceptedRejectedDueToUnsupportedSource() {
        // Arrange
        supportedOddTypes = EnumSet.of(MatchOdd.Type.ODD1);
        supportedProposalsSources = ImmutableSet.of("TEST3");
        init();
        BigDecimal riskAmount = new BigDecimal("20.00");
        doReturn(Optional.of(riskAmount)).when(riskAmountProvider).get(any(BigDecimal.class), any(BigDecimal.class));
        doReturn(true).when(supportedOddRangesProvider).isInRange(any(BigDecimal.class));

        long bettorId = 1L;
        long matchId = 1L;
        BigDecimal odd = new BigDecimal("1.50");
        MatchBetProposalRawDTO matchBetProposalRawDTO = MatchBetProposalRawDTO.builder().setProposalSource("TEST1")
                .setExpectedMatchResult("ODD1").setOdd(odd).setMatchId(matchId).build();
        Bettor bettor = new Bettor();
        bettor.setId(bettorId);
        bettor.addToResources(new BigDecimal("200.00"));

        //Act
        Optional<MatchBetProposalDTO> result = parametrizedBettorLogic.processBetProposal(matchBetProposalRawDTO, bettor);

        //Assert
        assertEquals(false, result.isPresent());


    }


    @Test
    public void betProposalAcceptedRejectedDueToUnsupportedOddType() {
        // Arrange
        supportedOddTypes = EnumSet.of(MatchOdd.Type.ODD2);
        supportedProposalsSources = ImmutableSet.of("TEST1");
        init();
        BigDecimal riskAmount = new BigDecimal("20.00");
        doReturn(Optional.of(riskAmount)).when(riskAmountProvider).get(any(BigDecimal.class), any(BigDecimal.class));
        doReturn(true).when(supportedOddRangesProvider).isInRange(any(BigDecimal.class));

        long bettorId = 1L;
        long matchId = 1L;
        BigDecimal odd = new BigDecimal("1.50");
        MatchBetProposalRawDTO matchBetProposalRawDTO = MatchBetProposalRawDTO.builder().setProposalSource("TEST1")
                .setExpectedMatchResult("ODD1").setOdd(odd).setMatchId(matchId).build();
        Bettor bettor = new Bettor();
        bettor.setId(bettorId);
        bettor.addToResources(new BigDecimal("200.00"));

        //Act
        Optional<MatchBetProposalDTO> result = parametrizedBettorLogic.processBetProposal(matchBetProposalRawDTO, bettor);

        //Assert
        assertEquals(false, result.isPresent());


    }


    @Test
    public void betProposalAcceptedRejectedDueToLackOfResources() {
        // Arrange
        supportedOddTypes = EnumSet.of(MatchOdd.Type.ODD1);
        supportedProposalsSources = ImmutableSet.of("TEST1");
        BigDecimal riskAmount = new BigDecimal("20.00");
        init();
        doReturn(Optional.of(riskAmount)).when(riskAmountProvider).get(any(BigDecimal.class), any(BigDecimal.class));
        doReturn(false).when(supportedOddRangesProvider).isInRange(any(BigDecimal.class));

        long bettorId = 1L;
        long matchId = 1L;
        BigDecimal odd = new BigDecimal("1.50");
        MatchBetProposalRawDTO matchBetProposalRawDTO = MatchBetProposalRawDTO.builder().setProposalSource("TEST1")
                .setExpectedMatchResult("ODD1").setOdd(odd).setMatchId(matchId).build();
        Bettor bettor = new Bettor();
        bettor.setId(bettorId);
        bettor.addToResources(new BigDecimal("200.00"));

        //Act
        Optional<MatchBetProposalDTO> result = parametrizedBettorLogic.processBetProposal(matchBetProposalRawDTO, bettor);

        //Assert
        assertEquals(false, result.isPresent());

    }

    public void betProposalAcceptedRejectedDueToUnsupportedOddsRange() {
        // Arrange
        supportedOddTypes = EnumSet.of(MatchOdd.Type.ODD1);
        supportedProposalsSources = ImmutableSet.of("TEST1");
        init();
        doReturn(Optional.empty()).when(riskAmountProvider).get(any(BigDecimal.class), any(BigDecimal.class));
        doReturn(true).when(supportedOddRangesProvider).isInRange(any(BigDecimal.class));

        long bettorId = 1L;
        long matchId = 1L;
        BigDecimal odd = new BigDecimal("1.50");
        MatchBetProposalRawDTO matchBetProposalRawDTO = MatchBetProposalRawDTO.builder().setProposalSource("TEST1")
                .setExpectedMatchResult("ODD1").setOdd(odd).setMatchId(matchId).build();
        Bettor bettor = new Bettor();
        bettor.setId(bettorId);
        bettor.addToResources(new BigDecimal("200.00"));

        //Act
        Optional<MatchBetProposalDTO> result = parametrizedBettorLogic.processBetProposal(matchBetProposalRawDTO, bettor);

        //Assert
        assertEquals(false, result.isPresent());

    }

    @Test
    public void test() {
        String init = "{\n" +
                "  \"@class\" : \"com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic\",\n" +
                "  \"riskAmountProvider\" : {\n" +
                "    \"@class\" : \"com.kk.betting.services.bettorhandling.common.service.riskamountselection.FixedValueProvider\",\n" +
                "    \"fixedRiskAmount\" : 20.00\n" +
                "  },\n" +
                "  \"supportedOddRangesProvider\" : {\n" +
                "    \"lowerLimit\" : 1.0,\n" +
                "    \"upperLimit\" : 10000.00\n" +
                "  },\n" +
                "  \"supportedOddTypes\" : [ \"ODD1\", \"ODD2\", \"ODDX\", \"ODD1X\", \"ODD12\", \"ODDX2\", \"ODDBTSY\", \"ODDBTSN\", \"ODDO05\", \"ODDO15\", \"ODDO25\", \"ODDO35\", \"ODDO45\", \"ODDO55\", \"ODDO65\", \"ODDU05\", \"ODDU15\", \"ODDU25\", \"ODDU35\", \"ODDU45\", \"ODDU55\", \"ODDU65\" ],\n" +
                "  \"supportedProposalsSources\" : [ \"TYPERSI.EFFICIENCY_20\" ]\n" +
                "}";


        Bettor bettor = new Bettor();
        bettor.setId(1L);
        bettor.addToResources(new BigDecimal("200.00"));

        bettor.logicParametersSerialized = init;

        Map<String, Object> params = bettor.getLogicParameters();

        BettorLogic logic = ObjectMapperFactory.get().convertValue(bettor.getLogicParameters(), BettorLogic.class);
//MatchBetProposalRawDTO{matchId=2248472, expectedMatchResult='ODDX2', odd=1.50, proposalSource='TYPERSI.EFFICIENCY_100,TYPERSI.EFFICIENCY_110,TYPERSI.EFFICIENCY_120,TYPERSI.EFFICIENCY_20,TYPERSI.EFFICIENCY_40,TYPERSI.EFFICIENCY_30,TYPERSI.EFFICIENCY_60,TYPERSI.EFFICIENCY_50,TYPERSI.EFFICIENCY_80,TYPERSI.EFFICIENCY_70,TYPERSI.EFFICIENCY_90'}
        MatchBetProposalRawDTO dto = MatchBetProposalRawDTO.builder()
                .setOdd(new BigDecimal("1.50"))
                .setProposalSource("TYPERSI.EFFICIENCY_100,TYPERSI.EFFICIENCY_110,TYPERSI.EFFICIENCY_120,TYPERSI.EFFICIENCY_20,TYPERSI.EFFICIENCY_40,TYPERSI.EFFICIENCY_30,TYPERSI.EFFICIENCY_60,TYPERSI.EFFICIENCY_50,TYPERSI.EFFICIENCY_80,TYPERSI.EFFICIENCY_70,TYPERSI.EFFICIENCY_90")
                .setExpectedMatchResult("ODDX2")
                .setMatchId(2248472L).build();

        Optional<MatchBetProposalDTO> result = logic.processBetProposal(dto, bettor);
        System.out.println("result" + result);

    }

    private void init() {
        riskAmountProvider = PowerMockito.mock(RiskAmountProvider.class);
        supportedOddRangesProvider = PowerMockito.mock(SupportedOddRangesProvider.class);
        parametrizedBettorLogic = new ParametrizedBettorLogic();
        parametrizedBettorLogic.setRiskAmountProvider(riskAmountProvider);
        parametrizedBettorLogic.setSupportedOddRangesProvider(supportedOddRangesProvider);
        parametrizedBettorLogic.setSupportedOddTypes(supportedOddTypes);
        parametrizedBettorLogic.setSupportedProposalsSources(supportedProposalsSources);
    }
}
