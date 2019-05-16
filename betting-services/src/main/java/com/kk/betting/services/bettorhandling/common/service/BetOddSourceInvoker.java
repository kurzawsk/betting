package com.kk.betting.services.bettorhandling.common.service;

import com.google.common.collect.Maps;
import com.kk.betting.domain.BettingProposalSource;
import com.kk.betting.domain.BettingProposalSourceMatch;
import com.kk.betting.domain.Match;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.common.dao.*;
import com.kk.betting.services.common.matcher.service.MappingService;
import com.kk.betting.services.common.service.WebPageBrowser;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by KK on 2017-05-21.
 */
@Named
@Singleton
public class BetOddSourceInvoker {

    @Inject
    private WebPageBrowser webPageBrowser;

    @Inject
    private BettingProposalSourceDao bettingProposalSourceDao;

    @Inject
    private BettingProposalSourceMatchDao bettingProposalSourceMatchDao;

    @Inject
    private MatchDao matchDao;

    @Inject
    private MatchOddDao matchOddDao;

    @Inject
    private BookmakerDao bookmakerDao;

    @Inject
    private MappingService mappingService;

    private Map<BettingProposalSource, BettingProposalSourceLogic> bettingLogicBinding = Maps.newConcurrentMap();

    @PostConstruct
    public void init() {
        initBettingProposalSources();
    }

    public List<MatchBetProposalRawDTO> invokeBetOddSourceLogic(BettingProposalSource bettingProposalSource) {
        return bettingLogicBinding.get(bettingProposalSource).getBettingProposals();
    }

    public Map<BettingProposalSource, BettingProposalSourceLogic> getBettingLogicBinding() {
        return bettingLogicBinding;
    }

    private void initBettingProposalSources() {
        List<BettingProposalSource> activeBettingProposalSources = bettingProposalSourceDao.getActiveBettingProposalSources();
        activeBettingProposalSources.stream().forEach(bettingProposalSource -> bettingLogicBinding.put(bettingProposalSource, initBettingProposalSourceLogic(bettingProposalSource)));
    }

    private BettingProposalSourceLogic initBettingProposalSourceLogic(BettingProposalSource bettingProposalSource) {
        BettingProposalSourceLogic bettingProposalSourceLogic;
        try {
            bettingProposalSourceLogic = (BettingProposalSourceLogic) CDI.current().select(Class.forName(bettingProposalSource.getLogicImpClass())).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<Long> alreadyProposedMatchIds = bettingProposalSourceMatchDao.getBettingProposalSourceMatchAfter(bettingProposalSource, LocalDateTime.now())
                .stream().map(BettingProposalSourceMatch::getMatch).map(Match::getId).collect(Collectors.toList());

        bettingProposalSourceLogic.init(alreadyProposedMatchIds, bettingProposalSource);
        return bettingProposalSourceLogic;
    }

}
