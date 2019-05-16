package com.kk.betting.services.common.service;

import com.google.common.collect.Maps;
import com.kk.betting.services.common.dao.BettingEventDao;
import com.kk.betting.services.common.dao.BettorDao;
import com.kk.betting.services.common.dao.MatchDao;
import com.kk.betting.services.common.dao.MatchOddDao;
import com.kk.betting.domain.Bettor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by KK on 2017-06-24.
 */
public abstract class AbstractBettingEventProcessor {

    private static final Log log = LogFactory.getLog(AbstractBettingEventProcessor.class);
    protected static final int DEFAULT_MAX_ATTEMPTS = 500;

    @Inject
    protected BettingEventDao bettingEventDao;

    @Inject
    protected MatchDao matchDao;

    @Inject
    protected MatchOddDao matchOddDao;

    @Inject
    protected BettorDao bettorDao;

    private Map<Long, Integer> attemptsLeftForBettorToBeFree = Maps.newConcurrentMap();

    protected Bettor waitForBettorToBeFreeAndLock(Long bettorId) {
        Bettor bettor = bettorDao.findRefreshedById(bettorId);
        attemptsLeftForBettorToBeFree.putIfAbsent(bettor.getId(), DEFAULT_MAX_ATTEMPTS);
        boolean isProgress = bettorDao.isBettorPendingProgress(bettor);
        while (isProgress && attemptsLeftForBettorToBeFree.get(bettor.getId()) > 0) {
            log.info("Waiting for betting to be completed for bettor: " + bettor.getId() + " attempts left = " + attemptsLeftForBettorToBeFree.get(bettor.getId()));
            try {
                TimeUnit.MILLISECONDS.sleep(50);
                attemptsLeftForBettorToBeFree.put(bettor.getId(), attemptsLeftForBettorToBeFree.get(bettor.getId()) - 1);
            } catch (InterruptedException e) {
                attemptsLeftForBettorToBeFree.remove(bettor);
                throw new RuntimeException(e);
            }
            isProgress = bettorDao.isBettorPendingProgress(bettor);
        }

        int attemptsLeft = attemptsLeftForBettorToBeFree.get(bettor.getId());
        if (attemptsLeft <= 0) {
            log.error("Timeout reached in waiting for bettor: " + bettor.getId());
        }
        attemptsLeftForBettorToBeFree.remove(bettor.getId());
        bettor.setBettingInProgress(true);
        bettorDao.persist(bettor);
        return bettor;

    }


}
