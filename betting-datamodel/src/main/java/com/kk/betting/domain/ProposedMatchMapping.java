package com.kk.betting.domain;

import com.kk.betting.domain.converter.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by KK on 2017-07-29.
 */

@Entity
@Table(name = "PROPOSED_MATCH_MAPPING")
public class ProposedMatchMapping extends ManagedEntityImp implements Serializable {

    public enum Status implements Serializable {
        NEW, ACCEPTED, REJECTED
    }

    @Column(name = "PROPOSED_HOME_TEAM_NAME", nullable = false)
    private String proposedHomeTeamName;

    @Column(name = "PROPOSED_AWAY_TEAM_NAME", nullable = false)
    private String proposedAwayTeamName;

    @ManyToOne(targetEntity = Team.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "HOME_TEAM_ID", nullable = false)
    private Team homeTeam;

    @ManyToOne(targetEntity = Team.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "AWAY_TEAM_ID", nullable = false)
    private Team awayTeam;

    @ManyToOne(targetEntity = Match.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "REFERENCE_MATCH_ID", nullable = false)
    private Match referenceMatch;

    @Column(name = "MATCH_START_TIME", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime matchStartTime;

    @Column(name = "HOME_SIMILARITY_FACTOR", nullable = false)
    private Double homeSimilarityFactor;

    @Column(name = "AWAY_SIMILARITY_FACTOR", nullable = false)
    private Double awaySimilarityFactor;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private Status status;

    @Column(name = "SOURCE_SYSTEM", nullable = false)
    private String sourceSystem;

    public String getProposedHomeTeamName() {
        return proposedHomeTeamName;
    }

    public void setProposedHomeTeamName(String proposedHomeTeamName) {
        this.proposedHomeTeamName = proposedHomeTeamName;
    }

    public String getProposedAwayTeamName() {
        return proposedAwayTeamName;
    }

    public void setProposedAwayTeamName(String proposedAwayTeamName) {
        this.proposedAwayTeamName = proposedAwayTeamName;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Match getReferenceMatch() {
        return referenceMatch;
    }

    public void setReferenceMatch(Match referenceMatch) {
        this.referenceMatch = referenceMatch;
    }

    public LocalDateTime getMatchStartTime() {
        return matchStartTime;
    }

    public void setMatchStartTime(LocalDateTime matchStartTime) {
        this.matchStartTime = matchStartTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getHomeSimilarityFactor() {
        return homeSimilarityFactor;
    }

    public void setHomeSimilarityFactor(Double homeSimilarityFactor) {
        this.homeSimilarityFactor = homeSimilarityFactor;
    }

    public Double getAwaySimilarityFactor() {
        return awaySimilarityFactor;
    }

    public void setAwaySimilarityFactor(Double awaySimilarityFactor) {
        this.awaySimilarityFactor = awaySimilarityFactor;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }
}
