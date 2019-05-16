package com.kk.betting.services.common.dao;

import com.kk.betting.domain.Team;

/**
 * Created by KK on 2017-06-25.
 */
public class TeamDao extends BaseJpaDao<Team, Long> {

    public TeamDao() {
        super(Team.class);
    }


}
