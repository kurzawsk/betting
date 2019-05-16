package com.kk.betting.services.common.dao;

import com.kk.betting.domain.BettingProposalSource;

import java.util.List;

/**
 * Created by KK on 2017-06-25.
 */
public class BettingProposalSourceDao extends BaseJpaDao<BettingProposalSource, Long> {

    private static final String ALL_ACTIVE_BETTING_PROPOSAL_SOURCES_QUERY = "from BettingProposalSource bps where bps.isActive=true";

    public BettingProposalSourceDao() {
        super(BettingProposalSource.class);
    }

    public List<BettingProposalSource> getActiveBettingProposalSources() {
        return (List<BettingProposalSource>) getStringBasedTypedQuery(ALL_ACTIVE_BETTING_PROPOSAL_SOURCES_QUERY).getResultList();
    }


}
