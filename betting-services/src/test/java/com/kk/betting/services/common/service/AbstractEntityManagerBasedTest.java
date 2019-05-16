package com.kk.betting.services.common.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractEntityManagerBasedTest {

    protected static final String PERSISTENCE_UNIT_NAME = "testPU";
    protected static final String HSQL_DRIVER_NAME = "org.hsqldb.jdbcDriver";
    protected static final String IN_MEMORY_DB_CONNECTION = "jdbc:hsqldb:mem:unit-testing-jpa";
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;
    protected Connection connection;

    public AbstractEntityManagerBasedTest() {
        try {
            setUp();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setUp() throws Exception {
        Class.forName(HSQL_DRIVER_NAME);
        connection = DriverManager.getConnection(IN_MEMORY_DB_CONNECTION, new Properties());
        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        Persistence.generateSchema(PERSISTENCE_UNIT_NAME, null);
        entityManager = Mockito.spy(new DelegatingEntityManager(entityManagerFactory.createEntityManager()));

    }

    protected void startTransaction() {
        entityManager.getTransaction().begin();
        entityManager.joinTransaction();
    }

    protected void rollbackTransaction() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
    }

    protected void commitTransaction() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().commit();
        }
    }

    public static class DelegatingEntityManager implements EntityManager {

        private EntityManager delegateManager;

        DelegatingEntityManager(EntityManager delegateManager) {
            this.delegateManager = delegateManager;
        }

        @Override
        public void persist(Object entity) {
            delegateManager.persist(entity);
        }

        @Override
        public <T> T merge(T entity) {
            return delegateManager.merge(entity);
        }

        @Override
        public void remove(Object entity) {
            delegateManager.remove(entity);
        }

        @Override
        public <T> T find(Class<T> entityClass, Object primaryKey) {
            return delegateManager.find(entityClass, primaryKey);
        }

        @Override
        public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
            return delegateManager.find(entityClass, primaryKey, properties);
        }

        @Override
        public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
            return delegateManager.find(entityClass, primaryKey, lockMode);
        }

        @Override
        public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
            return delegateManager.find(entityClass, primaryKey, lockMode, properties);
        }

        @Override
        public <T> T getReference(Class<T> entityClass, Object primaryKey) {
            return delegateManager.getReference(entityClass, primaryKey);
        }

        @Override
        public void flush() {
            delegateManager.flush();
        }

        @Override
        public void setFlushMode(FlushModeType flushMode) {
            delegateManager.setFlushMode(flushMode);

        }

        @Override
        public FlushModeType getFlushMode() {
            return delegateManager.getFlushMode();
        }

        @Override
        public void lock(Object entity, LockModeType lockMode) {
            delegateManager.lock(entity, lockMode);

        }

        @Override
        public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
            delegateManager.lock(entity, lockMode, properties);
        }

        @Override
        public void refresh(Object entity) {
            delegateManager.refresh(entity);
        }

        @Override
        public void refresh(Object entity, Map<String, Object> properties) {
            delegateManager.refresh(entity, properties);
        }

        @Override
        public void refresh(Object entity, LockModeType lockMode) {
            delegateManager.refresh(entity, lockMode);
        }

        @Override
        public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
            delegateManager.refresh(entity, lockMode, properties);
        }

        @Override
        public void clear() {
            delegateManager.clear();
        }

        @Override
        public void detach(Object entity) {
            delegateManager.detach(entity);
        }

        @Override
        public boolean contains(Object entity) {
            return delegateManager.contains(entity);
        }

        @Override
        public LockModeType getLockMode(Object entity) {
            return delegateManager.getLockMode(entity);
        }

        @Override
        public void setProperty(String propertyName, Object value) {
            delegateManager.setProperty(propertyName, value);
        }

        @Override
        public Map<String, Object> getProperties() {
            return delegateManager.getProperties();
        }

        @Override
        public Query createQuery(String qlString) {
            return delegateManager.createQuery(qlString);
        }

        @Override
        public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
            return delegateManager.createQuery(criteriaQuery);
        }

        @Override
        public Query createQuery(CriteriaUpdate updateQuery) {
            return delegateManager.createQuery(updateQuery);
        }

        @Override
        public Query createQuery(CriteriaDelete deleteQuery) {
            return delegateManager.createQuery(deleteQuery);
        }

        @Override
        public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
            return delegateManager.createQuery(qlString, resultClass);
        }

        @Override
        public Query createNamedQuery(String name) {
            return delegateManager.createNamedQuery(name);
        }

        @Override
        public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
            return delegateManager.createNamedQuery(name, resultClass);
        }

        @Override
        public Query createNativeQuery(String sqlString) {
            return delegateManager.createNativeQuery(sqlString);
        }

        @Override
        public Query createNativeQuery(String sqlString, Class resultClass) {
            return delegateManager.createNativeQuery(sqlString, resultClass);
        }

        @Override
        public Query createNativeQuery(String sqlString, String resultSetMapping) {
            return delegateManager.createNativeQuery(sqlString, resultSetMapping);
        }

        @Override
        public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
            return delegateManager.createNamedStoredProcedureQuery(name);
        }

        @Override
        public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
            return delegateManager.createStoredProcedureQuery(procedureName);
        }

        @Override
        public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {
            return delegateManager.createStoredProcedureQuery(procedureName, resultClasses);
        }

        @Override
        public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {
            return delegateManager.createStoredProcedureQuery(procedureName, resultSetMappings);
        }

        @Override
        public void joinTransaction() {
            delegateManager.joinTransaction();
        }

        @Override
        public boolean isJoinedToTransaction() {
            return delegateManager.isJoinedToTransaction();
        }

        @Override
        public <T> T unwrap(Class<T> cls) {
            return delegateManager.unwrap(cls);
        }

        @Override
        public Object getDelegate() {
            return delegateManager.getDelegate();
        }

        @Override
        public void close() {
            delegateManager.close();
        }

        @Override
        public boolean isOpen() {
            return delegateManager.isOpen();
        }

        @Override
        public EntityTransaction getTransaction() {
            return delegateManager.getTransaction();
        }

        @Override
        public EntityManagerFactory getEntityManagerFactory() {
            return delegateManager.getEntityManagerFactory();
        }

        @Override
        public CriteriaBuilder getCriteriaBuilder() {
            return delegateManager.getCriteriaBuilder();
        }

        @Override
        public Metamodel getMetamodel() {
            return delegateManager.getMetamodel();
        }

        @Override
        public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
            return delegateManager.createEntityGraph(rootType);
        }

        @Override
        public EntityGraph<?> createEntityGraph(String graphName) {
            return delegateManager.createEntityGraph(graphName);
        }

        @Override
        public EntityGraph<?> getEntityGraph(String graphName) {
            return delegateManager.getEntityGraph(graphName);
        }

        @Override
        public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
            return delegateManager.getEntityGraphs(entityClass);
        }
    }

}
