package com.kk.betting.services.common.matcher.service;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimap;
import com.kk.betting.services.common.matcher.dto.TeamMatchingResult;
import com.kk.betting.services.common.matcher.service.cleanse.Cleanser;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by KK on 2017-07-28.
 */
@Named
public class TeamMatcher {

    private static final double MAX_SIMILARITY_SCORE = 1D;
    private static final double THRESHOLD_SIMILARITY_SCORE = 0.01D;

    private Multimap<String, Long> referenceTeamsNames;
    private Multimap<String, Long> cleansedReferenceTeamsNames;

    @Inject
    private Cleanser cleanser;

    public void init(Multimap<Long, String> teamsNames) {
        ImmutableListMultimap.Builder<String, Long> rawNamesMapBuilder = ImmutableListMultimap.builder();
        ImmutableListMultimap.Builder<String, Long> cleansedNamesMapBuilder = ImmutableListMultimap.builder();

        teamsNames.asMap().entrySet().stream().forEach(e -> e.getValue().stream().forEach(name ->
        {
            rawNamesMapBuilder.put(name, e.getKey());
            cleansedNamesMapBuilder.put(cleanser.cleanse(name), e.getKey());
        }));

        this.cleansedReferenceTeamsNames = cleansedNamesMapBuilder.build();
        this.referenceTeamsNames = rawNamesMapBuilder.build();
    }

    public List<TeamMatchingResult> findExactlyMatchingTeam(String teamName) {
      return referenceTeamsNames.get(teamName).stream().map(id-> new TeamMatchingResult(teamName, id, teamName, MAX_SIMILARITY_SCORE)).collect(Collectors.toList());
    }

    public List<TeamMatchingResult> findSimilarTeams(String teamName) {
        String cleansedTeamName = cleanser.cleanse(teamName);
        return cleansedReferenceTeamsNames.asMap().entrySet().stream().map(e -> e.getValue().stream()
                .map(i -> new TeamMatchingResult(e.getKey(), i, cleansedTeamName, getSimilarityScore(e.getKey(), cleansedTeamName))))
                .flatMap(Function.identity()).filter(tmr -> tmr.getSimilarityFactor() > THRESHOLD_SIMILARITY_SCORE)
                .sorted((tmr1, tmr2) -> tmr2.getSimilarityFactor().compareTo(tmr1.getSimilarityFactor())).collect(Collectors.toList());
    }

    private double getSimilarityScore(String nameA, String nameB) {

        double scoreMatched = 0;
        String[] nameAWords = nameA.split(" ");
        String[] nameBWords = nameB.split(" ");

        int aWordsCubedLength = Stream.of(nameAWords).mapToInt(String::length).map(n -> n * n * n).sum();
        int bWordsCubedLength = Stream.of(nameBWords).mapToInt(String::length).map(n -> n * n * n).sum();

        int maxWordsLength = Math.max(aWordsCubedLength, bWordsCubedLength);
        int minWordsLength = Math.min(aWordsCubedLength, bWordsCubedLength);

        for (String a : nameAWords) {
            for (String b : nameBWords) {
                if (Objects.equals(a, b)) {
                    scoreMatched += a.length() * a.length() * a.length();
                }
            }
        }

        return scoreMatched * ((double) minWordsLength / (maxWordsLength * maxWordsLength));
    }

}
