package com.kk.betting.domain;

import com.google.common.collect.Sets;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TEAM")
public class Team extends ManagedEntityImp implements Serializable {

    private static final long serialVersionUID = 7522307707740753136L;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade = CascadeType.PERSIST)
    private Set<TeamName> alternativeNames = Sets.newHashSet();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "homeTeam")
    private Set<Match> homeMatches = Sets.newHashSet();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "awayTeam")
    private Set<Match> awayMatches = Sets.newHashSet();

    public Team() {
    }

    public Set<Match> getHomeMatches() {
        return homeMatches;
    }

    public Set<Match> getAwayMatches() {
        return awayMatches;
    }

    public Set<Match> getMatches() {
        return Sets.union(homeMatches, awayMatches);
    }

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<TeamName> getAlternativeNames() {
        return alternativeNames;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return name != null ? name.equals(team.name) : team.name == null;

    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name + " [" + id + "]";
    }
}
