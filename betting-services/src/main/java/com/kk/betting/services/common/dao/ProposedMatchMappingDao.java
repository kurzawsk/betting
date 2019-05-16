package com.kk.betting.services.common.dao;

import com.kk.betting.domain.ProposedMatchMapping;

import java.util.List;

/**
 * Created by KK on 2017-07-29.
 */
public class ProposedMatchMappingDao extends BaseJpaDao<ProposedMatchMapping, Long> {

    private static final String HOME_TEAM_ID = "#HOME_TEAM_ID";
    private static final String AWAY_TEAM_ID = "#AWAY_TEAM_ID";

    private static final String PROPOSED_HOME_TEAM_NAME = "#PROPOSED_HOME_TEAM_NAME";
    private static final String PROPOSED_AWAY_TEAM_NAME = "#PROPOSED_AWAY_TEAM_NAME";
    private static final String MAPPING_ALREADY_PRESENT_QUERY = "SELECT COUNT(*) FROM PROPOSED_MATCH_MAPPING WHERE HOME_TEAM_ID = " + HOME_TEAM_ID +
            " AND AWAY_TEAM_ID = " + AWAY_TEAM_ID + " AND PROPOSED_HOME_TEAM_NAME = '" + PROPOSED_HOME_TEAM_NAME + "' AND PROPOSED_AWAY_TEAM_NAME = '" + PROPOSED_AWAY_TEAM_NAME + "'";

    private static final String MAPPINGS_TO_PROCESS_QUERY =" from ProposedMatchMapping where status='NEW'";

    public ProposedMatchMappingDao() {
        super(ProposedMatchMapping.class);
    }


    public List<? extends ProposedMatchMapping> getProposedMatchMappingToProcess(){
        return getStringBasedTypedQuery(MAPPINGS_TO_PROCESS_QUERY).getResultList();
    }

    public boolean isMappingAlreadyPresent(Long homeTeamId, Long awayTeamId, String proposedHomeTeamName, String proposedAwayTeamName) {
        String query = MAPPING_ALREADY_PRESENT_QUERY
                .replace(HOME_TEAM_ID, homeTeamId.toString())
                .replace(AWAY_TEAM_ID, awayTeamId.toString())
                .replace(PROPOSED_HOME_TEAM_NAME, proposedHomeTeamName)
                .replace(PROPOSED_AWAY_TEAM_NAME, proposedAwayTeamName);
        return ((Number) getEntityManager().createNativeQuery(query).getSingleResult()).intValue() > 0;
    }

}
