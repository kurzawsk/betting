package com.kk.betting.services.common.dao;

import com.kk.betting.domain.Bettor;
import com.kk.betting.domain.Match;

import java.util.List;

/**
 * Created by KK on 2017-06-25.
 */
public class BettorDao extends BaseJpaDao<Bettor, Long> {

    private static final String ACTIVE_BETTORS_QUERY = "from Bettor b where b.active=true";
    private static final String BETTOR_ID = "#BETTOR_ID";
    private static final String MATCH_ID = "#MATCH_ID";
    private static final String MATCH_BETTOR_EVENTS_COUNT_QUERY = "select count(*) from BETTING_EVENT where MATCH_ID=" + MATCH_ID + " and BETTOR_ID=" + BETTOR_ID;

    public BettorDao() {
        super(Bettor.class);
    }

    public List<? extends Bettor> getActiveBettors() {
        return getStringBasedTypedQuery(ACTIVE_BETTORS_QUERY).getResultList();
    }

    public boolean isMatchAlreadyBetByBettor(Match match, Bettor bettor) {
        return ((Number) getEntityManager().createNativeQuery(MATCH_BETTOR_EVENTS_COUNT_QUERY.replaceAll(MATCH_ID, String.valueOf(match.getId())).
                replaceAll(BETTOR_ID, String.valueOf(bettor.getId()))).getSingleResult()).intValue() > 0;
    }

    public boolean isBettorPendingProgress(Bettor bettor) {
        return findRefreshedById(bettor.getId()).isBettingInProgress();
    }
}
