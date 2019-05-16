package com.kk.betting.services.bettorhandling.typersi.dao;

import com.kk.betting.domain.TypersiHistoryEntry;
import com.kk.betting.services.common.dao.BaseJpaDao;

/**
 * Created by KK on 2017-08-30.
 */
public class TypersiHistoryEntryDao extends BaseJpaDao<TypersiHistoryEntry,Long> {
    public TypersiHistoryEntryDao() {
        super(TypersiHistoryEntry.class);
    }

    public void deleteAll(){
        getStringBasedQuery("DELETE FROM TypersiHistoryEntry").executeUpdate();
    }
}
