package com.kk.betting.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by KK on 2017-07-14.
 */
public class BettingEventDTO implements Serializable{
    private Long id;
    private BigDecimal resourcesAvailableBeforeEvent;
    private BigDecimal resourcesAvailableAfterEvent;
    private BigDecimal betOdd;
    private String eventType;
    private LocalDateTime eventTime;
    private LocalDateTime matchStartTime;
    private String match;
    private String expectedMatchResult;
    private String proposalSource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getResourcesAvailableBeforeEvent() {
        return resourcesAvailableBeforeEvent;
    }

    public void setResourcesAvailableBeforeEvent(BigDecimal resourcesAvailableBeforeEvent) {
        this.resourcesAvailableBeforeEvent = resourcesAvailableBeforeEvent;
    }

    public BigDecimal getResourcesAvailableAfterEvent() {
        return resourcesAvailableAfterEvent;
    }

    public void setResourcesAvailableAfterEvent(BigDecimal resourcesAvailableAfterEvent) {
        this.resourcesAvailableAfterEvent = resourcesAvailableAfterEvent;
    }

    public BigDecimal getBetOdd() {
        return betOdd;
    }

    public void setBetOdd(BigDecimal betOdd) {
        this.betOdd = betOdd;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getExpectedMatchResult() {
        return expectedMatchResult;
    }

    public void setExpectedMatchResult(String expectedMatchResult) {
        this.expectedMatchResult = expectedMatchResult;
    }

    public String getProposalSource() {
        return proposalSource;
    }

    public void setProposalSource(String proposalSource) {
        this.proposalSource = proposalSource;
    }

    public LocalDateTime getMatchStartTime() {
        return matchStartTime;
    }

    public void setMatchStartTime(LocalDateTime matchStartTime) {
        this.matchStartTime = matchStartTime;
    }
}
