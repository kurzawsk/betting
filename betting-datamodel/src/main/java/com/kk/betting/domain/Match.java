package com.kk.betting.domain;

import com.google.common.base.MoreObjects;
import com.kk.betting.domain.converter.LocalDateTimeAttributeConverter;
import org.hibernate.annotations.SortComparator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "MATCH")
public class Match extends ManagedEntityImp implements Serializable {

    private static final long serialVersionUID = -1L;

    @Column(name = "IDENTIFIER", nullable = false, unique = true)
    private String identifier;

    @ManyToOne(targetEntity = Team.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "HOME_TEAM_ID", nullable = false)
    private Team homeTeam;

    @ManyToOne(targetEntity = Team.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "AWAY_TEAM_ID", nullable = false)
    private Team awayTeam;

    @Embedded
    private MatchResult result;

    @Column(name = "START_TIME", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime startTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "match")
    @SortComparator(MatchOddTimeComparator.class)
    private List<MatchOdd> matchOdds;


    public Match() {
    }

    public Match(String identifier, Team homeTeam, Team awayTeam, LocalDateTime startTime) {
        this.identifier = identifier;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = startTime;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public List<MatchOdd> getMatchOdds() {
        return matchOdds;
    }

    public void setMatchOdds(List<MatchOdd> matchOdds) {
        this.matchOdds = matchOdds;
    }

    public MatchResult getResult() {
        return MoreObjects.firstNonNull(result, MatchResult.EMPTY_VALUE);
    }

    public void setResult(MatchResult result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match)) return false;

        Match match = (Match) o;

        if (homeTeam != null ? !homeTeam.equals(match.homeTeam) : match.homeTeam != null) return false;
        return awayTeam != null ? awayTeam.equals(match.awayTeam) : match.awayTeam == null && Objects.equals(startTime, match.getStartTime());

    }

    public static class MatchOddTimeComparator implements Comparator<MatchOdd> {
        @Override
        public int compare(MatchOdd mo1, MatchOdd mo2) {
            return mo2.getTimestamp().compareTo(mo1.getTimestamp());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam, startTime);
    }

    @Override
    public String toString() {
        return homeTeam + "-" + awayTeam + " " + startTime + "  [" + id + "]";
    }

}
