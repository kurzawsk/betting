package com.kk.betting.services.bettorhandling.common.service;

import com.kk.betting.services.common.service.Converter;
import com.kk.betting.domain.BettingEvent;
import com.kk.betting.domain.Match;
import com.kk.betting.dto.BettingEventDTO;
import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;

/**
 * Created by KK on 2017-07-14.
 */
public class BettingEventConverter implements Converter<BettingEvent, BettingEventDTO> {

    @Override
    public BettingEventDTO convertToDTO(BettingEvent domain) {
        BettingEventDTO bettingEventDTO = new BettingEventDTO();
        bettingEventDTO.setId(domain.getId());
        bettingEventDTO.setBetOdd(domain.getBetOdd());
        bettingEventDTO.setProposalSource(domain.getProposalSource());
        bettingEventDTO.setEventTime(domain.getEventTime());
        bettingEventDTO.setEventType(domain.getEventType().getLabel());
        bettingEventDTO.setResourcesAvailableAfterEvent(domain.getResourcesAvailableAfterEvent());
        bettingEventDTO.setResourcesAvailableBeforeEvent(domain.getResourcesAvailableBeforeEvent());
        bettingEventDTO.setMatch(convertMatch(domain.getMatch()));
        bettingEventDTO.setMatchStartTime(domain.getMatch() != null ? domain.getMatch().getStartTime() : null);
        bettingEventDTO.setExpectedMatchResult(formatResult(domain));
        return bettingEventDTO;
    }

    @Override
    public BettingEvent convertToDomain(BettingEventDTO dto) {
        throw new UnsupportedOperationException();
    }

    private String convertMatch(Match match) {
        return match != null ?  match.getHomeTeam().getName() + " - " + match.getAwayTeam().getName() : StringUtils.EMPTY;
    }

    private String formatResult(BettingEvent domain) {
        if (domain.getType() == BettingEvent.Type.MATCH_BET) {
            return domain.getExpectedMatchResult().getLabel();
        } else if (domain.getType() == BettingEvent.Type.MATCH_WITHDRAWN || domain.getType() == BettingEvent.Type.BET_SUCCESSFUL || domain.getType() == BettingEvent.Type.BET_UNSUCCESSFUL) {
            return domain.getExpectedMatchResult().getLabel() + "/" + domain.getMatch().getResult().toCleanFormat();
        }
        return StringUtils.EMPTY;
    }
}
