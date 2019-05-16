package com.kk.betting.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by KK on 2017-07-29.
 */
public class ProposedMatchMappingDTO implements Serializable {

    private long id;
    private LocalDateTime proposedStartTime;
    private long homeTeamId;
    private List<String> homeTeamNames;
    private String proposedHomeTeamName;
    private long awayTeamId;
    private String proposedAwayTeamName;
    private List<String> awayTeamNames;
    private double homeTeamSimilarityFactor;
    private double awayTeamSimilarityFactor;
    private long matchId;
    private LocalDateTime matchStartTime;
    private String sourceSystem;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getProposedStartTime() {
        return proposedStartTime;
    }

    public void setProposedStartTime(LocalDateTime proposedStartTime) {
        this.proposedStartTime = proposedStartTime;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public LocalDateTime getMatchStartTime() {
        return matchStartTime;
    }

    public void setMatchStartTime(LocalDateTime matchStartTime) {
        this.matchStartTime = matchStartTime;
    }

    public long getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(long homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public List<String> getHomeTeamNames() {
        return homeTeamNames;
    }

    public void setHomeTeamNames(List<String> homeTeamNames) {
        this.homeTeamNames = homeTeamNames;
    }

    public String getProposedHomeTeamName() {
        return proposedHomeTeamName;
    }

    public void setProposedHomeTeamName(String proposedHomeTeamName) {
        this.proposedHomeTeamName = proposedHomeTeamName;
    }

    public long getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(long awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getProposedAwayTeamName() {
        return proposedAwayTeamName;
    }

    public void setProposedAwayTeamName(String proposedAwayTeamName) {
        this.proposedAwayTeamName = proposedAwayTeamName;
    }

    public List<String> getAwayTeamNames() {
        return awayTeamNames;
    }

    public void setAwayTeamNames(List<String> awayTeamNames) {
        this.awayTeamNames = awayTeamNames;
    }

    public double getHomeTeamSimilarityFactor() {
        return homeTeamSimilarityFactor;
    }

    public void setHomeTeamSimilarityFactor(double homeTeamSimilarityFactor) {
        this.homeTeamSimilarityFactor = homeTeamSimilarityFactor;
    }

    public double getAwayTeamSimilarityFactor() {
        return awayTeamSimilarityFactor;
    }

    public void setAwayTeamSimilarityFactor(double awayTeamSimilarityFactor) {
        this.awayTeamSimilarityFactor = awayTeamSimilarityFactor;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }
}
