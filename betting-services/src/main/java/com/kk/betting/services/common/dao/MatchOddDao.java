package com.kk.betting.services.common.dao;

import com.google.common.base.Joiner;
import com.kk.betting.domain.MatchOdd;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by KK on 2017-06-25.
 */
public class MatchOddDao extends BaseJpaDao<MatchOdd, Long> {

    private static final String BOOKMAKER_ID = "#BOOKMAKER_ID";
    private static final String MATCH_ID = "#MATCH_ID";
    private static final String MATCH_ODDS_QUERY = "SELECT MO1.* FROM MATCH_ODD MO1 JOIN" +
            " (SELECT MATCH_ID AS M_MATCH_ID, MAX(BOOKMAKER_ID) AS M_BOOKMAKER_ID, MAX(TIMESTAMP) AS M_TIMESTAMP FROM MATCH_ODD " +
            "WHERE BOOKMAKER_ID = " + BOOKMAKER_ID + " AND MATCH_ID IN (" + MATCH_ID + ") " +
            "GROUP BY MATCH_ID) MO2 ON  MO2.M_TIMESTAMP= MO1.TIMESTAMP AND MO1.BOOKMAKER_ID = MO2.M_BOOKMAKER_ID and MO1.MATCH_ID = MO2.M_MATCH_ID";

    //private static final String MATCH_ID = "matchId";

    public MatchOddDao() {
        super(MatchOdd.class);
    }

    public Optional<MatchOdd> getLatestMatchOdd(Long matchId, Long bookmakerId) {
        String sql = MATCH_ODDS_QUERY.replace(BOOKMAKER_ID, bookmakerId.toString()).replace(MATCH_ID, matchId.toString());
        List<MatchOdd> odds = getNativeQuery(sql).getResultList();
        return odds.stream().filter(odd -> bookmakerId.equals(odd.getBookmaker().getId())).findFirst();
    }

    public List<MatchOdd> getLatestMatchOdd(Set<Long> matchIds, Long bookmakerId) {
        if (matchIds.isEmpty()) {
            return Collections.emptyList();
        }
        String sql = MATCH_ODDS_QUERY.replace(MATCH_ID, Joiner.on(",").join(matchIds)).replace(BOOKMAKER_ID, bookmakerId.toString());
        return getNativeQuery(sql).getResultList();
    }
}
