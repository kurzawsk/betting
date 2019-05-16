package com.kk.betting.services.common.dao;

import com.kk.betting.domain.BettingEvent;

import java.util.List;

/**
 * Created by KK on 2017-06-25.
 */
public class BettingEventDao extends BaseJpaDao<BettingEvent, Long> {

    private static final String BETTOR_ID = "#BETTOR_ID";
    private static final String PENDING_MATCHES_QUERY = "select " +
            "m.id, be.betOdd, be.expectedMatchResult, be.resourcesAvailableBeforeEvent, be.resourcesAvailableAfterEvent, bt.id, be.proposalSource " +
            "from BettingEvent as be inner join be.match as m  " +
            "inner join be.bettor bt " +
            "where m.result.homeScore is null and m.result.awayScore is null and be.eventType = '" + BettingEvent.Type.MATCH_BET + "'";

    private static final String BETTING_EVENT_DISCREPANCY_QUERY = "SELECT\n" +
            "  be1.bettor_id,\n" +
            "  be1.match_id,\n" +
            "  be1.event_time,\n" +
            "  m.start_time\n" +
            "FROM\n" +
            "  (SELECT *\n" +
            "   FROM betting_event\n" +
            "   WHERE betting_event_type = '" + BettingEvent.Type.MATCH_BET + "') be1 LEFT JOIN (SELECT *\n" +
            "                                                          FROM betting_event\n" +
            "                                                          WHERE betting_event_type <> '" + BettingEvent.Type.MATCH_BET + "'\n" +
            "                                                         ) be2\n" +
            "    ON (be1.bettor_id = be2.bettor_id AND be1.match_id = be2.match_id)\n" +
            "  JOIN match m ON m.id = be1.match_id AND m.homescore IS NOT NULL\n" +
            "                  AND be2.match_id IS NULL\n" +
            "ORDER BY be1.bettor_id, match_id, be1.event_time";

    private static final String PENDING_MATCHES_FOR_BETTOR_QUERY = "select be.* from (" +
            "(select * from betting_event where betting_event_type = '" + BettingEvent.Type.MATCH_BET + "') be join match m on be.match_id = m.id and m.homescore is null and BETTOR_ID=" + BETTOR_ID + ")";

    private static final String BETTOR_RESOURCES_ALIGNMENT_QUERY = "select * from betting_event be where betting_event_type in ('" +
            BettingEvent.Type.MONEY_ADDED + "','" + BettingEvent.Type.MONEY_WITHDRAWN + "') and be.BETTOR_ID=" + BETTOR_ID;

    private static final String PROFIT_CHANGES_FOR_BETTOR_QUERY = "select * from betting_event where betting_event_type in ('" + BettingEvent.Type.MONEY_ADDED + "','" + BettingEvent.Type.MONEY_WITHDRAWN + "') and BETTOR_ID=" + BETTOR_ID;


    public BettingEventDao() {
        super(BettingEvent.class);
    }


    public List<? extends BettingEvent> getBettorResourcesAlignmentBettingEvents(Long bettorId) {
        return getNativeQuery(BETTOR_RESOURCES_ALIGNMENT_QUERY.replaceAll(BETTOR_ID, bettorId.toString())).getResultList();
    }


    public List<? extends BettingEvent> gePendingMatchBettingEvents(Long bettorId) {
        return getNativeQuery(PENDING_MATCHES_FOR_BETTOR_QUERY.replaceAll(BETTOR_ID, bettorId.toString())).getResultList();
    }

    public List<? extends BettingEvent> geProfitChangesBettingEvents(Long bettorId) {
        return getNativeQuery(PROFIT_CHANGES_FOR_BETTOR_QUERY.replaceAll(BETTOR_ID, bettorId.toString())).getResultList();
    }

    public List<Object[]> getPendingBettingEventAndMatchBettorDetails() {
        return getStringBasedQuery(PENDING_MATCHES_QUERY).getResultList();
    }

    public List<Object[]> getBettingEventDiscrepancies() {
        return getEntityManager().createNativeQuery(BETTING_EVENT_DISCREPANCY_QUERY).getResultList();
    }
}
