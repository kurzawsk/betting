package com.kk.betting.services.bettorhandling.typersi.service;

import com.kk.betting.domain.Match;
import com.kk.betting.domain.Team;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.common.dao.MatchDao;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;

/**
 * Created by KK on 2017-08-06.
 */
//TODO - tmp ignore
    @Ignore
@RunWith(PowerMockRunner.class)
@PrepareForTest({MatchDao.class})
public class TypersiLogicTest {

    private MatchDao matchDao = PowerMockito.mock(MatchDao.class);

    @Mock
    private TypersiBetProposalService typersiBetProposalService;

    @InjectMocks
    private TypersiLogic typersiLogic;

    @Test
    public void testNoProposals() {
        doReturn(Collections.emptyList()).when(typersiBetProposalService).getTodaysBettorOdds(any(List.class));
        doReturn(Collections.emptyList()).when(typersiBetProposalService).getTomorrowsBettorOdds(any(List.class));

        doReturn(Collections.emptyList()).when(typersiBetProposalService).getTodaysMostEfficientBettorsOdds();
        doReturn(Collections.emptyList()).when(typersiBetProposalService).getTomorrowsMostEfficientBettorsOdds();

        doReturn(Collections.emptyList()).when(typersiBetProposalService).getTodaysTop5BettorsOdds();
        doReturn(Collections.emptyList()).when(typersiBetProposalService).getTomorrowsTop5BettorsOdds();


        List<MatchBetProposalRawDTO> result = typersiLogic.prepareBettingProposals();


        assertThat(result, is(empty()));
    }

    @Test
    public void testFilterOnStartTime() {
        Match match1 = new Match("test1", new Team("t1"), new Team("t2"), LocalDateTime.now());
        Match match2 = new Match("test2", new Team("t3"), new Team("t4"), LocalDateTime.now().plusDays(1));
        Match match3 = new Match("test2", new Team("t5"), new Team("t6"), LocalDateTime.now().minusHours(2));

        match1.setId(1L);
        match2.setId(2L);
        match3.setId(3L);

        doReturn(match1).when(matchDao).findById(eq(1L));
        doReturn(match2).when(matchDao).findById(eq(2L));
        doReturn(match3).when(matchDao).findById(eq(3L));

        MatchBetProposalRawDTO dto1 = MatchBetProposalRawDTO.builder().setMatchId(match1.getId()).setExpectedMatchResult("ODD1")
                .setProposalSource("Typersi.Top5").setOdd(new BigDecimal("1.50")).build();

        MatchBetProposalRawDTO dto2 = MatchBetProposalRawDTO.builder().setMatchId(match2.getId()).setExpectedMatchResult("ODD2")
                .setProposalSource("Typersi.MostEfficient").setOdd(new BigDecimal("2.50")).build();

        MatchBetProposalRawDTO dto3 = MatchBetProposalRawDTO.builder().setMatchId(match3.getId()).setExpectedMatchResult("ODDX")
                .setProposalSource("Typersi.MostEfficient").setOdd(new BigDecimal("3.10")).build();

        doReturn(Collections.emptyList()).when(typersiBetProposalService).getTodaysBettorOdds(any(List.class));
        doReturn(Collections.emptyList()).when(typersiBetProposalService).getTomorrowsBettorOdds(any(List.class));

        doReturn(Arrays.asList(dto2, dto3)).when(typersiBetProposalService).getTodaysMostEfficientBettorsOdds();
        doReturn(Collections.emptyList()).when(typersiBetProposalService).getTomorrowsMostEfficientBettorsOdds();

        doReturn(Collections.singletonList(dto1)).when(typersiBetProposalService).getTodaysTop5BettorsOdds();
        doReturn(Collections.emptyList()).when(typersiBetProposalService).getTomorrowsTop5BettorsOdds();


        List<MatchBetProposalRawDTO> result = typersiLogic.prepareBettingProposals();


        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).getMatchId());
        assertEquals("ODD2", result.get(0).getExpectedMatchResult());
        assertEquals("Typersi.MostEfficient", result.get(0).getProposalSource());
        assertEquals(new BigDecimal("2.50"), result.get(0).getOdd());
    }

    @Test
    public void testMatchMerging(){
        Match match1 = new Match("test1", new Team("t1"), new Team("t2"), LocalDateTime.now().plusDays(2));
        Match match2 = new Match("test2", new Team("t3"), new Team("t4"), LocalDateTime.now().plusDays(1));

        match1.setId(1L);
        match2.setId(2L);

        doReturn(match1).when(matchDao).findById(eq(1L));
        doReturn(match2).when(matchDao).findById(eq(2L));

        MatchBetProposalRawDTO dto1 = MatchBetProposalRawDTO.builder().setMatchId(match1.getId()).setExpectedMatchResult("ODD1")
                .setProposalSource("Typersi.Top5").setOdd(new BigDecimal("1.50")).build();

        MatchBetProposalRawDTO dto2 = MatchBetProposalRawDTO.builder().setMatchId(match2.getId()).setExpectedMatchResult("ODD2")
                .setProposalSource("Typersi.MostEfficient").setOdd(new BigDecimal("2.50")).build();


        MatchBetProposalRawDTO dto3 = MatchBetProposalRawDTO.builder().setMatchId(match1.getId()).setExpectedMatchResult("ODD1")
                .setProposalSource("Typersi.MostEfficient").setOdd(new BigDecimal("1.80")).build();

        MatchBetProposalRawDTO dto4 = MatchBetProposalRawDTO.builder().setMatchId(match1.getId()).setExpectedMatchResult("ODDX")
                .setProposalSource("Typersi.TestTyper").setOdd(new BigDecimal("2.30")).build();

        doReturn(Collections.singletonList(dto4)).when(typersiBetProposalService).getTodaysBettorOdds(any(List.class));
        doReturn(Collections.emptyList()).when(typersiBetProposalService).getTomorrowsBettorOdds(any(List.class));

        doReturn(Arrays.asList(dto2, dto3)).when(typersiBetProposalService).getTodaysMostEfficientBettorsOdds();
        doReturn(Collections.emptyList()).when(typersiBetProposalService).getTomorrowsMostEfficientBettorsOdds();

        doReturn(Collections.singletonList(dto1)).when(typersiBetProposalService).getTodaysTop5BettorsOdds();
        doReturn(Collections.emptyList()).when(typersiBetProposalService).getTomorrowsTop5BettorsOdds();

        List<MatchBetProposalRawDTO> result = typersiLogic.prepareBettingProposals();

        assertEquals(3, result.size());

        Map<Long, List<MatchBetProposalRawDTO>> proposalsById = result.stream()
                .collect(Collectors.groupingBy(MatchBetProposalRawDTO::getMatchId, Collectors.toList()));

        assertEquals(2, proposalsById.get(1L).size());
        assertEquals(1, proposalsById.get(2L).size());
    }
}
