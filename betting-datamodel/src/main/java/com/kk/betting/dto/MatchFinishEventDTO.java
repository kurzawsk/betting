package com.kk.betting.dto;

import java.io.Serializable;

/**
 * Created by KK on 2017-02-05.
 */
public class MatchFinishEventDTO implements Serializable {

    private long matchId;
    private String matchResult;

    public MatchFinishEventDTO(long matchId, String matchResult) {
        this.matchId = matchId;
        this.matchResult = matchResult;
    }

    public long getMatchId() {
        return matchId;
    }

    public String getMatchResult() {
        return matchResult;
    }

}
