package com.kk.betting.domain;

import javax.persistence.*;

/**
 * Created by KK on 2017-07-29.
 */
@Entity
@Table(name = "TEAM_NAME")
public class TeamName extends ManagedEntityImp {

    @ManyToOne(targetEntity = Team.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "TEAM_ID", nullable = false)
    private Team team;

    @Column(name = "PROPOSED_TEAM_NAME", nullable = false, unique = true)
    private String name;

    public TeamName(){
    }

    public TeamName(Team team, String name) {
        this.team = team;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamName)) return false;
        if (!super.equals(o)) return false;

        TeamName teamName = (TeamName) o;

        return team.equals(teamName.team) && name.equals(teamName.name);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + team.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
