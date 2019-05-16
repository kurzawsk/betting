package com.kk.betting.domain;

import com.kk.betting.domain.converter.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "BETTING_EVENT",indexes = {@Index(columnList = "BETTOR_ID"),
        @Index(columnList = "MATCH_ID"),@Index(columnList = "MATCH_ID,BETTOR_ID")})
public class BettingEvent extends ManagedEntityImp implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Type implements Serializable {

        BET_SUCCESSFUL("Bet successful"), MATCH_WITHDRAWN("Match withdrawn"), MATCH_BET("Match bet"), BET_UNSUCCESSFUL("Bet unsuccessful"), MONEY_WITHDRAWN("Money withdrawn"),MONEY_ADDED("Money added");

        private String label;

        Type(String label) {
            this.label = label;
        }

        public String  getLabel() {
            return label;
        }

    }

    @ManyToOne( cascade = CascadeType.REMOVE)
    @JoinColumn(name = "BETTOR_ID", nullable = false)
    private Bettor bettor;

    @Column(name = "RESOURCES_BEFORE_EVENT", precision = 9, scale = 2, nullable = false)
    private BigDecimal resourcesAvailableBeforeEvent;

    @Column(name = "RESOURCES_AFTER_EVENT", precision = 9, scale = 2, nullable = false)
    private BigDecimal resourcesAvailableAfterEvent;

    @Column(name = "BET_COURSE")
    private BigDecimal betOdd;

    @Enumerated(EnumType.STRING)
    @Column(name = "BETTING_EVENT_TYPE", nullable = false)
    private Type eventType;

    @Column(name = "EVENT_TIME", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime eventTime;

    @ManyToOne(targetEntity = Match.class)
    @JoinColumn(name = "MATCH_ID")
    private Match match;

    @Enumerated(EnumType.STRING)
    @Column(name = "EXPECTED_MATCH_RESULT")
    private MatchOdd.Type expectedMatchResult;

    @Column(name = "PROPOSAL_SOURCE", length  = 4000)
    private String proposalSource;

    public BettingEvent() {

    }

    public Bettor getBettor() {
        return bettor;
    }

    public void setBettor(Bettor bettor) {
        this.bettor = bettor;
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

    public Type getType() {
        return eventType;
    }

    public void setType(Type eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public MatchOdd.Type getExpectedMatchResult() {
        return expectedMatchResult;
    }

    public void setExpectedMatchResult(MatchOdd.Type selectedMatchResult) {
        this.expectedMatchResult = selectedMatchResult;
    }

    public BigDecimal getBetOdd() {
        return betOdd;
    }

    public void setBetOdd(BigDecimal betOdd) {
        this.betOdd = betOdd;
    }

    public Type getEventType() {
        return eventType;
    }

    public void setEventType(Type eventType) {
        this.eventType = eventType;
    }

    public String getProposalSource() {
        return proposalSource;
    }

    public void setProposalSource(String proposalSource) {
        this.proposalSource = proposalSource;
    }
}
