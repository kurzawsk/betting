package com.kk.betting.services.bettorhandling.common.service;

import com.google.common.collect.Lists;
import com.kk.betting.domain.BettingProposalSource;
import com.kk.betting.domain.BettingProposalSourceMatch;
import com.kk.betting.domain.Match;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.common.dao.BettingProposalSourceMatchDao;
import com.kk.betting.services.common.dao.BookmakerDao;
import com.kk.betting.services.common.dao.MatchDao;
import com.kk.betting.services.common.dao.MatchOddDao;
import com.kk.betting.services.common.matcher.service.MappingService;
import com.kk.betting.services.common.service.WebPageBrowser;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by KK on 2017-05-06.
 */
public abstract class BettingProposalSourceLogic {

    private static final int MAX_NO_OF_MATCHES_PROPOSED_SIMULTANEOUSLY = 100;
    @Inject
    protected WebPageBrowser browser;

    @Inject
    protected BookmakerDao bookmakerDao;

    @Inject
    protected MatchDao matchDao;

    @Inject
    protected MatchOddDao matchOddDao;

    @Inject
    protected BettingProposalSourceMatchDao bettingProposalSourceMatchDao;

    @Inject
    protected MappingService mappingService;

    protected List<Long> alreadyProposedMatchIds;
    protected BettingProposalSource parentSystem;

    public void init(List<Long> alreadyProposedMatchIds, BettingProposalSource parentSystem) {
        this.alreadyProposedMatchIds = alreadyProposedMatchIds;
        this.parentSystem = parentSystem;
    }


   public List<MatchBetProposalRawDTO> getBettingProposals() {
        removeOldMatches();
        List<MatchBetProposalRawDTO> result = prepareBettingProposals().stream()
                .filter(m -> !alreadyProposedMatchIds.contains(m.getMatchId())).collect(Collectors.groupingBy(MatchBetProposalRawDTO::getMatchId, Collectors.toSet()))
                .entrySet().stream().limit(MAX_NO_OF_MATCHES_PROPOSED_SIMULTANEOUSLY).map(Map.Entry::getValue).flatMap(Set::stream).collect(Collectors.toList());
        addNewMatches(result.stream().map(MatchBetProposalRawDTO::getMatchId).collect(Collectors.toList()));
        return result;
    }

    protected abstract List<MatchBetProposalRawDTO> prepareBettingProposals();


    private void removeOldMatches() {
        List<Long> oldMatchIds = alreadyProposedMatchIds.stream()
                .filter(id -> matchDao.findById(id).getStartTime().isBefore(LocalDateTime.now())).collect(Collectors.toList());
        alreadyProposedMatchIds.removeAll(oldMatchIds);
    }

    private void addNewMatches(List<Long> matchIds) {
        List<BettingProposalSourceMatch> bettingProposalSourceMatches = Lists.newArrayListWithExpectedSize(matchIds.size());
        matchIds.stream().distinct().forEach(id -> {
            Match match = matchDao.findById(id);
            bettingProposalSourceMatches.add(new BettingProposalSourceMatch(match, parentSystem));
            alreadyProposedMatchIds.add(id);
        });

        bettingProposalSourceMatchDao.persist(bettingProposalSourceMatches);
    }


}
