package com.kk.betting.services.matchdatacollection.service;

import java.time.LocalDateTime;

/**
 * Created by KK on 2017-02-27.
 */
public interface MatchDataCollector {

    void checkMatchResults();

    void checkMatchResults(LocalDateTime referenceDate);

    void updateMatchOdds();

    void findAndInsertNewMatches();
}
