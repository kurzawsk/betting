package com.kk.betting.services.common.dao;

import com.kk.betting.services.common.util.CommonConstants;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by KK on 2017-06-25.
 */
public class BaseJpaDao<T, K> implements GenericDao<T, K> {

    private static final String SUFFIX_FROM = " FROM ";

    @PersistenceContext(unitName = CommonConstants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;
    protected Class<? extends T> entityClass;

    public BaseJpaDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public final void refresh(T entity) {
        entityManager.refresh(entity);
    }

    @Override
    public final void persist(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public final void persist(Iterable<? extends T> entities) {
        for (T entity : entities) {
            entityManager.persist(entity);
        }
    }

    @Override
    public final void remove(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public List<? extends T> findAll() {
        return getAllEntitiesQuery().getResultList();
    }

    @Override
    public final T findById(K id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public final T findRefreshedById(K id) {
        T entity = entityManager.find(entityClass, id);
        entityManager.refresh(entity);
        return entity;
    }


    protected final TypedQuery<? extends T> getStringBasedTypedQuery(String query) {
        return entityManager.createQuery(query, entityClass);
    }

    protected final TypedQuery<? extends T> getAllEntitiesQuery() {
        return getStringBasedTypedQuery(SUFFIX_FROM + entityClass.getSimpleName());
    }

    protected final Query getNativeQuery(String sql) {
        return entityManager.createNativeQuery(sql,entityClass);
    }

    protected final Query getStringBasedQuery(String query) {
        return entityManager.createQuery(query);
    }

    protected final EntityManager getEntityManager() {
        return entityManager;
    }


}
