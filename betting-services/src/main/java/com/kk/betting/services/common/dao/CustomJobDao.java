package com.kk.betting.services.common.dao;

import com.kk.betting.domain.Bettor;
import com.kk.betting.domain.CustomJob;

import java.util.List;

/**
 * Created by KK on 2017-09-02.
 */
public class CustomJobDao extends BaseJpaDao<CustomJob,Long> {

    private static final String ACTIVE_CUSTOM_JOBS_QUERY = "from CustomJob cj where cj.active=true";

    public CustomJobDao() {
        super(CustomJob.class);
    }


    public List<? extends CustomJob> getActiveCustomJobs() {
        return getStringBasedTypedQuery(ACTIVE_CUSTOM_JOBS_QUERY).getResultList();
    }
}
