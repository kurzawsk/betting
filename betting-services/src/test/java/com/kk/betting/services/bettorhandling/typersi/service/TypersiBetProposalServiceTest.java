package com.kk.betting.services.bettorhandling.typersi.service;

import com.google.common.collect.Lists;
import com.kk.betting.domain.Match;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.bettorhandling.typersi.dao.TypersiRawDao;
import com.kk.betting.services.bettorhandling.typersi.dto.TypersiMatchBetProposalDTO;
import com.kk.betting.services.common.matcher.service.MappingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
@RunWith(MockitoJUnitRunner.class)
public class TypersiBetProposalServiceTest {

    @Mock
    private MappingService mappingService;

    @Mock
    private TypersiRawDao typersiRawDao;

    @InjectMocks
    private TypersiBetProposalService typersiBetProposalService;

    @Test
    public void testNoProposals() {

        doReturn(Collections.emptyList()).when(typersiRawDao).getTodaysTop5BettorsOdds();
        doReturn(Collections.emptyList()).when(typersiRawDao).getTomorrowsTop5BettorsOdds();

        doReturn(Collections.emptyList()).when(typersiRawDao).getTodaysMostEfficientBettorsOdds();
        doReturn(Collections.emptyList()).when(typersiRawDao).getTomorrowsMostEfficientBettorsOdds();

        doReturn(Collections.emptyList()).when(typersiRawDao).getTodaysBettorsOdds(any(List.class));
        doReturn(Collections.emptyList()).when(typersiRawDao).getTomorrowsBettorsOdds(any(List.class));

        List<MatchBetProposalRawDTO> result = Lists.newArrayList();
        result.addAll(typersiBetProposalService.getTodaysTop5BettorsOdds());
        result.addAll(typersiBetProposalService.getTomorrowsTop5BettorsOdds());

        result.addAll(typersiBetProposalService.getTodaysMostEfficientBettorsOdds());
        result.addAll(typersiBetProposalService.getTomorrowsMostEfficientBettorsOdds());

        result.addAll(typersiBetProposalService.getTodaysBettorOdds(Lists.newArrayList("test bettor1")));
        result.addAll(typersiBetProposalService.getTomorrowsBettorOdds(Lists.newArrayList("test bettor 2")));

        assertThat(result, is(empty()));
    }

    @Test
    public void testGet5TopTomorrows() {

        //Arrange
        List<TypersiMatchBetProposalDTO> typersiMatchBetProposalDTO = Lists.newArrayList();
        TypersiMatchBetProposalDTO dto1 = new TypersiMatchBetProposalDTO();
        dto1.setBettor("test bettor");
        dto1.setMatch("Home Team - Away Team");
        dto1.setOdd("1.5");
        dto1.setProposedResult("1");
        dto1.setTime("21:00");

        TypersiMatchBetProposalDTO dto2 = new TypersiMatchBetProposalDTO();
        dto2.setBettor("test bettor2");
        dto2.setMatch("T1 - T2");
        dto2.setOdd("2.0");
        dto2.setProposedResult("X");
        dto2.setTime("17:00");

        typersiMatchBetProposalDTO.add(dto1);
        typersiMatchBetProposalDTO.add(dto2);

        Match match1 = new Match();
        match1.setId(1L);
        match1.setStartTime(LocalDateTime.now().withHour(21).withMinute(0).withSecond(0).withNano(0).plusDays(1L));

        doReturn(typersiMatchBetProposalDTO).when(typersiRawDao).getTomorrowsTop5BettorsOdds();
        doReturn(Optional.of(match1)).when(mappingService).findMatch(eq("Home Team"), eq("Away Team"), any(LocalDateTime.class));
        doReturn(Optional.empty()).when(mappingService).findMatch(eq("T1"), eq("T2"), any(LocalDateTime.class));


        //Act
        List<MatchBetProposalRawDTO> result = typersiBetProposalService.getTomorrowsTop5BettorsOdds();

        //Assert
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getMatchId());
        assertEquals("ODD1", result.get(0).getExpectedMatchResult());
        assertEquals("TYPERSI.TOP5", result.get(0).getProposalSource());

    }





}
