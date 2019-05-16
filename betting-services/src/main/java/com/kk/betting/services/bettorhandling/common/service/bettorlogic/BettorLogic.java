package com.kk.betting.services.bettorhandling.common.service.bettorlogic;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kk.betting.domain.Bettor;
import com.kk.betting.dto.MatchBetProposalDTO;
import com.kk.betting.dto.MatchBetProposalRawDTO;

import java.util.Map;
import java.util.Optional;

/**
 * Created by KK on 2017-08-05.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface BettorLogic {

    Optional<MatchBetProposalDTO> processBetProposal(MatchBetProposalRawDTO matchBetProposalRawDTO, Bettor bettor);
}
