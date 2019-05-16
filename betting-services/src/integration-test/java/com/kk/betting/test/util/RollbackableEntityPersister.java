package com.kk.betting.test.util;

import com.google.common.collect.Lists;
import com.kk.betting.domain.ManagedEntity;
import com.kk.betting.services.common.util.CommonConstants;


import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

/**
 * Created by KK on 2017-06-05.
 */
@Named
public class RollbackableEntityPersister  {

    @PersistenceContext(unitName = CommonConstants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    private List<ManagedEntity> entitiesToRemove = Lists.newLinkedList();

    public void persistAndScheduleForRemoval(ManagedEntity entity) {
        entityManager.persist(entity);
        entitiesToRemove.add(entity);
    }

    public void removeScheduledEntities() {
        Collections.reverse(entitiesToRemove);
        entitiesToRemove.stream().map(e -> entityManager.merge(e)).forEach(e -> {
            entityManager.refresh(e);
            entityManager.remove(e);
        });
        entitiesToRemove.clear();
    }
}