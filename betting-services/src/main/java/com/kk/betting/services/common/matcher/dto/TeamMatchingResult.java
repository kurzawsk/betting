package com.kk.betting.services.common.matcher.dto;

/**
 * Created by KK on 2017-07-28.
 */
public class TeamMatchingResult {

    private String sourceTeamName;
    private Long teamId;
    private String teamName;
    private Double similarityFactor;

    public TeamMatchingResult(String sourceTeamName, Long teamId, String teamName, Double similarityFactor) {
        this.sourceTeamName = sourceTeamName;
        this.teamId = teamId;
        this.teamName = teamName;
        this.similarityFactor = similarityFactor;
    }

    public String getSourceTeamName() {
        return sourceTeamName;
    }

    public void setSourceTeamName(String sourceTeamName) {
        this.sourceTeamName = sourceTeamName;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Double getSimilarityFactor() {
        return similarityFactor;
    }

    public void setSimilarityFactor(Double similarityFactor) {
        this.similarityFactor = similarityFactor;
    }

    @Override
    public String toString() {
        return "TeamMatchingResult{" +
                "sourceTeamName='" + sourceTeamName + '\'' +
                ", teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", similarityFactor=" + similarityFactor +
                '}';
    }
}
