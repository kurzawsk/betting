package com.kk.betting.services.common.dao;

import com.kk.betting.domain.BettingProposalSource;
import com.kk.betting.domain.BettingProposalSourceMatch;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by KK on 2017-06-25.
 */
public class BettingProposalSourceMatchDao extends BaseJpaDao<BettingProposalSourceMatch, Long> {
    private static final String START_TIME = "startTime";
    private static final String BETTING_PROPOSAL_SOURCE = "bettingProposalSource";
    private static final String BETTING_PROPOSAL_SOURCE_MATCH_AFTER_TIMESTAMP_QUERY = " from BettingProposalSourceMatch bpsm where bpsm.match.startTime > :" + START_TIME + " and bpsm.bettingProposalSource = :" + BETTING_PROPOSAL_SOURCE;

    public BettingProposalSourceMatchDao() {
        super(BettingProposalSourceMatch.class);
    }

    public List<? extends BettingProposalSourceMatch> getBettingProposalSourceMatchAfter(BettingProposalSource bettingProposalSource, LocalDateTime timestamp) {
        return getStringBasedTypedQuery(BETTING_PROPOSAL_SOURCE_MATCH_AFTER_TIMESTAMP_QUERY)
                .setParameter(START_TIME, timestamp)
                .setParameter(BETTING_PROPOSAL_SOURCE, bettingProposalSource).getResultList();
    }

}
