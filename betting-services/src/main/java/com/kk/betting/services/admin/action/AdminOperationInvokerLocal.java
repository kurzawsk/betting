package com.kk.betting.services.admin.action;

import javax.ejb.Local;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by KK on 2017-06-29.
 */
@Local
public interface AdminOperationInvokerLocal {
    void checkMatchResults();

    void updateMatchOdds();

    void findAndInsertNewMatches();

    void sendBettingProposal(Long matchId, String expectedMatchResult, BigDecimal odd, String sourceName);

    void prepareMatchReport(LocalDateTime from, LocalDateTime to);

    void prepareAllActiveBettorsHistoryReport();

    void invokeBetOddSource(Long bettingProposalSourceId);

}
