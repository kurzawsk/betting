package com.kk.betting.services.common.dao;

import com.kk.betting.domain.Match;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by KK on 2017-06-25.
 */
public class MatchDao extends BaseJpaDao<Match, Long> {

    private static final String QUERY_MATCHES_UNKNOWN_RESULT = "from Match m where result.homeScore is null and  result.awayScore is null";
    private static final String SUFFIX_ORDER_BY_START_TIME_ASC = " order by startTime asc";
    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String HOME_TEAM_ID = "homeTeamId";
    private static final String AWAY_TEAM_ID = "awayTeamId";
    private static final String START_TIME = "startTime";
    private static final String MATCH_START_TIME_BETWEEN_DATES_QUERY = "from Match m where m.startTime > :" + FROM + " and m.startTime < :" + TO;
    private static final String PENDING_MATCHES_BETWEEN_TEAMS_QUERY = "from Match m where m.homeTeam.id = :" + HOME_TEAM_ID +
            " and m.awayTeam.id =:" + AWAY_TEAM_ID + " and result.homeScore is null and result.awayScore is null";

    private static final String PENDING_MATCHES_BETWEEN_TEAMS_WITH_START_TIME_QUERY = "from Match m where m.homeTeam.id = :" + HOME_TEAM_ID +
            " and m.awayTeam.id =:" + AWAY_TEAM_ID + " and m.startTime = :" + START_TIME + " and  result.homeScore is null and result.awayScore is null";

    public MatchDao() {
        super(Match.class);
    }

    public List<? extends Match> getUnfinishedMatchesByStartTime() {
        return getStringBasedTypedQuery(QUERY_MATCHES_UNKNOWN_RESULT + SUFFIX_ORDER_BY_START_TIME_ASC).getResultList();
    }

    public List<? extends Match> getMatchesStartingBetween(LocalDateTime from, LocalDateTime to) {
        return getStringBasedTypedQuery(MATCH_START_TIME_BETWEEN_DATES_QUERY).setParameter(FROM, from).setParameter(TO, to).getResultList();
    }

    public Match getPendingMatchBetweenTeams(Long homeTeamId, Long awayTeamId, LocalDateTime startTime) {
        List<? extends Match> result = getStringBasedTypedQuery(PENDING_MATCHES_BETWEEN_TEAMS_WITH_START_TIME_QUERY).
                setParameter(HOME_TEAM_ID, homeTeamId).setParameter(AWAY_TEAM_ID, awayTeamId).setParameter(START_TIME, startTime).getResultList();
        if (result.size() > 1) {
            throw new IllegalStateException("There are " + result.size() + " pending matches between teams: " + homeTeamId + ", " + awayTeamId + ", start time: " + startTime + ", matches:" + result.stream().map(Match::getId).collect(Collectors.toList()));
        } else if (result.size() == 1) {
            return result.get(0);
        } else {
            return null;
        }
    }


    public List<? extends Match> getPendingMatchesBetweenTeams(Long homeTeamId, Long awayTeamId) {
        return getStringBasedTypedQuery(PENDING_MATCHES_BETWEEN_TEAMS_QUERY).
                setParameter(HOME_TEAM_ID, homeTeamId).setParameter(AWAY_TEAM_ID, awayTeamId).getResultList();

    }


}
