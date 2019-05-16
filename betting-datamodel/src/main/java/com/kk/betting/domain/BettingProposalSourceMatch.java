package com.kk.betting.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by KK on 2017-05-20.
 */

@Entity
@Table(name = "BETTING_PROPOSAL_SOURCE_MATCH")
public class BettingProposalSourceMatch extends ManagedEntityImp {

    @ManyToOne(targetEntity = Match.class)
    @JoinColumn(name = "MATCH_ID", nullable = false)
    private Match match;


    @ManyToOne(targetEntity = BettingProposalSource.class)
    @JoinColumn(name = "BETTING_PROPOSAL_SOURCE_ID", nullable = false)
    private BettingProposalSource bettingProposalSource;

    public BettingProposalSourceMatch() {
    }


    public BettingProposalSourceMatch(Match match, BettingProposalSource bettingProposalSource) {
        this.match = match;
        this.bettingProposalSource = bettingProposalSource;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public BettingProposalSource getBettingProposalSource() {
        return bettingProposalSource;
    }

    public void setBettingProposalSource(BettingProposalSource bettingProposalSource) {
        this.bettingProposalSource = bettingProposalSource;
    }
}
