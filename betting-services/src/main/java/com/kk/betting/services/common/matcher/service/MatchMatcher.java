package com.kk.betting.services.common.matcher.service;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kk.betting.domain.Match;
import com.kk.betting.domain.ProposedMatchMapping;
import com.kk.betting.domain.Team;
import com.kk.betting.domain.TeamName;
import com.kk.betting.services.common.dao.MatchDao;
import com.kk.betting.services.common.dao.TeamDao;
import com.kk.betting.services.common.matcher.dto.TeamMatchingResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by KK on 2017-07-28.
 */
@Named
public class MatchMatcher {

    private static final int MAX_TEAM_SIMILARITIES = 1000;

    @Inject
    private MatchDao matchDao;

    @Inject
    private TeamDao teamDao;

    @Inject
    private TeamMatcher teamMatcher;

    public void loadTeams() {
        ImmutableMultimap.Builder<Long, String> builder = ImmutableMultimap.builder();
        for (Team team : teamDao.findAll()) {
            builder.put(team.getId(), team.getName());
            builder.putAll(team.getId(), team.getAlternativeNames().stream().map(TeamName::getName).collect(Collectors.toList()));
        }
        teamMatcher.init(builder.build());
    }

    public Optional<Match> findMatch(String homeTeamName, String awayTeamName, LocalDateTime startTime) {
        List<TeamMatchingResult> potentialHomeTeams = teamMatcher.findExactlyMatchingTeam(homeTeamName);
        List<TeamMatchingResult> potentialAwayTeams = teamMatcher.findExactlyMatchingTeam(awayTeamName);
        List<Match> potentialMatches = Lists.newArrayList();

        for (TeamMatchingResult potentialHomeTeam : potentialHomeTeams) {
            potentialAwayTeams.stream().filter(potentialAwayTeam -> Objects.nonNull(potentialHomeTeam) && Objects.nonNull(potentialAwayTeam)).forEach(potentialAwayTeam -> {
                if (Objects.nonNull(startTime)) {
                    Optional.ofNullable(matchDao.getPendingMatchBetweenTeams(potentialHomeTeam.getTeamId(), potentialAwayTeam.getTeamId(), startTime)).ifPresent(
                            match -> potentialMatches.add(match));
                } else {
                    potentialMatches.addAll(matchDao.getPendingMatchesBetweenTeams(potentialHomeTeam.getTeamId(), potentialAwayTeam.getTeamId()));
                }
            });
        }

        if (potentialMatches.size() > 1 && Objects.nonNull(startTime)) {
            throw new IllegalArgumentException("Found two exactly matching matches for: " + homeTeamName + ", " + awayTeamName + ", " + startTime + ", potential Matches: " + potentialMatches);
        } else if (potentialMatches.size() == 1) {
            return Optional.ofNullable(potentialMatches.get(0));
        }
        return Optional.empty();
    }

    public List<ProposedMatchMapping> findSimilarMatches(String homeTeamName, String awayTeamName, LocalDateTime startTime, String sourceSystem) {
        List<TeamMatchingResult> potentialHomeTeams = teamMatcher.findSimilarTeams(homeTeamName);
        List<TeamMatchingResult> potentialAwayTeams = teamMatcher.findSimilarTeams(awayTeamName);
        List<TeamsSimilarity> teamsSimilarities = potentialHomeTeams.stream().map(ht -> potentialAwayTeams.stream().map(at -> new TeamsSimilarity(ht.getTeamId(), ht.getSimilarityFactor(), at.getTeamId(), at.getSimilarityFactor())))
                .flatMap(Function.identity()).sorted((s1,s2)->Double.compare(s2.getHomeTeamSimilarityFactor() * s2.getAwayTeamSimilarityFactor(), s1.getHomeTeamSimilarityFactor() * s1.getAwayTeamSimilarityFactor())).limit(MAX_TEAM_SIMILARITIES).collect(Collectors.toList());

        List<ProposedMatchMapping> proposedMatchMappings = Lists.newArrayList();
        for (TeamsSimilarity teamsSimilarity : teamsSimilarities) {
            List<? extends Match> similarMatches = matchDao.getPendingMatchesBetweenTeams(teamsSimilarity.getHomeTeamId(), teamsSimilarity.getAwayTeamId());
            proposedMatchMappings.addAll(similarMatches.stream().map(match -> createProposedMatchMapping(teamsSimilarity.getHomeTeamId(), teamsSimilarity.getHomeTeamSimilarityFactor(), homeTeamName, teamsSimilarity.getAwayTeamId(), teamsSimilarity.getAwayTeamSimilarityFactor(), awayTeamName, startTime, match, sourceSystem)).collect(Collectors.toList()));
        }

        Map<Map.Entry<Team, Team>, List<ProposedMatchMapping>> uniqueMappingsByTeam = proposedMatchMappings.stream()
                .collect(Collectors.groupingBy(p -> Maps.immutableEntry(p.getHomeTeam(), p.getAwayTeam()), Collectors.toList()));

        List<ProposedMatchMapping> uniqueMappings = Lists.newArrayList();
        uniqueMappings.addAll(uniqueMappingsByTeam.values().stream().map(mappings -> mappings.stream().max((m1, m2) -> Double.compare(m1.getHomeSimilarityFactor() * m1.getAwaySimilarityFactor(), m2.getHomeSimilarityFactor() * m2.getAwaySimilarityFactor())).get()).collect(Collectors.toList()));

        return uniqueMappings;
    }

    private ProposedMatchMapping createProposedMatchMapping(long homeTeamId, double homeTeamSimilarityFactor, String proposedHomeTeamName, long awayTeamId, double awayTeamSimilarityFactor, String proposedAwayTeamName, LocalDateTime matchStartTime, Match referenceMatch, String sourceSystem) {
        ProposedMatchMapping proposedMatchMapping = new ProposedMatchMapping();
        proposedMatchMapping.setHomeTeam(teamDao.findById(homeTeamId));
        proposedMatchMapping.setHomeSimilarityFactor(homeTeamSimilarityFactor);
        proposedMatchMapping.setProposedHomeTeamName(proposedHomeTeamName);
        proposedMatchMapping.setAwayTeam(teamDao.findById(awayTeamId));
        proposedMatchMapping.setAwaySimilarityFactor(awayTeamSimilarityFactor);
        proposedMatchMapping.setProposedAwayTeamName(proposedAwayTeamName);
        proposedMatchMapping.setReferenceMatch(referenceMatch);
        proposedMatchMapping.setMatchStartTime(matchStartTime);
        proposedMatchMapping.setStatus(ProposedMatchMapping.Status.NEW);
        proposedMatchMapping.setSourceSystem(sourceSystem);
        return proposedMatchMapping;
    }


    private static class TeamsSimilarity {
        private long homeTeamId;
        private double homeTeamSimilarityFactor;
        private long awayTeamId;
        private double awayTeamSimilarityFactor;

        public TeamsSimilarity(long homeTeamId, double homeTeamSimilarityFactor, long awayTeamId, double awayTeamSimilarityFactor) {
            this.homeTeamId = homeTeamId;
            this.homeTeamSimilarityFactor = homeTeamSimilarityFactor;
            this.awayTeamId = awayTeamId;
            this.awayTeamSimilarityFactor = awayTeamSimilarityFactor;
        }

        public long getHomeTeamId() {
            return homeTeamId;
        }

        public double getHomeTeamSimilarityFactor() {
            return homeTeamSimilarityFactor;
        }

        public long getAwayTeamId() {
            return awayTeamId;
        }

        public double getAwayTeamSimilarityFactor() {
            return awayTeamSimilarityFactor;
        }

    }
}
