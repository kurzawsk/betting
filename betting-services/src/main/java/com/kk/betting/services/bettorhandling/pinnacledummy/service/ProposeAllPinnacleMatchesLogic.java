package com.kk.betting.services.bettorhandling.pinnacledummy.service;

import com.google.common.collect.Lists;
import com.kk.betting.domain.Bookmaker;
import com.kk.betting.domain.Match;
import com.kk.betting.domain.MatchOdd;
import com.kk.betting.domain.MatchResult;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.bettorhandling.common.service.BettingProposalSourceLogic;

import javax.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by KK on 2017-05-06.
 * Proposes ALL bet types for all matches, based on latest MatchOdd Value
 */
@Named
public class ProposeAllPinnacleMatchesLogic extends BettingProposalSourceLogic {

    private static final String PINNACLE_BOOKMAKER_URL = "http://affiliates.pinnaclesports.com";
    private static final int MIN_DAYS_TO_MATCH_START = 1;
    private static final int MAX_DAYS_TO_MATCH_START = 4;

    @Override
    public List<MatchBetProposalRawDTO> prepareBettingProposals() {
        Bookmaker targetBookmaker = getTargetBookmaker();
        List<Match> matchesOfInterest = matchDao.getMatchesStartingBetween(LocalDateTime.now().plusDays(MIN_DAYS_TO_MATCH_START), LocalDateTime.now().plusDays(MAX_DAYS_TO_MATCH_START))
                .stream().filter(match -> match.getResult().asType() == MatchResult.Type.UNKNOWN).collect(Collectors.toList());

        List<MatchBetProposalRawDTO> bettingProposals = Lists.newLinkedList();
        List<MatchOdd> odds = matchOddDao.getLatestMatchOdd(matchesOfInterest.stream().map(Match::getId).collect(Collectors.toSet()), targetBookmaker.getId());

        for (MatchOdd odd : odds) {
            for (MatchOdd.Type oddType : MatchOdd.Type.values()) {
                BigDecimal oddValue = odd.getOdd(oddType);
                if (Objects.nonNull(oddValue)) {
                    bettingProposals.add(MatchBetProposalRawDTO.builder().
                            setMatchId(odd.getMatch().getId()).
                            setExpectedMatchResult(oddType.name()).setOdd(oddValue).
                            setProposalSource(ProposeAllPinnacleMatchesLogic.class.getSimpleName()).build());
                }
            }
        }

        return bettingProposals;
    }

    private Bookmaker getTargetBookmaker() {
        return bookmakerDao.findAll().stream().filter(b -> PINNACLE_BOOKMAKER_URL.equals(b.getWebPageUrl())).findFirst().get();
    }
}
