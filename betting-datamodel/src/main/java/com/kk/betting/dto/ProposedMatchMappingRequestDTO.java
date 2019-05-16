package com.kk.betting.dto;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by KK on 2017-07-29.
 */
public class ProposedMatchMappingRequestDTO implements Serializable {

    @NotNull
    private String homeTeam;
    @NotNull
    private String awayTeam;
    @NotNull
    private LocalDateTime startTime;

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
