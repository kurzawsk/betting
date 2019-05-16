package com.kk.betting.services.common.matcher.service;

import com.google.common.collect.Lists;
import com.kk.betting.domain.TeamName;
import com.kk.betting.services.common.service.Converter;
import com.kk.betting.domain.ProposedMatchMapping;
import com.kk.betting.domain.Team;
import com.kk.betting.dto.ProposedMatchMappingDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by KK on 2017-07-29.
 */
public class ProposedMatchMappingConverter implements Converter<ProposedMatchMapping, ProposedMatchMappingDTO> {
    @Override
    public ProposedMatchMappingDTO convertToDTO(ProposedMatchMapping domain) {
        ProposedMatchMappingDTO dto = new ProposedMatchMappingDTO();
        if (Objects.nonNull(domain.getId())) {
            dto.setId(domain.getId());
        }
        dto.setMatchId(domain.getReferenceMatch().getId());
        dto.setMatchStartTime(domain.getReferenceMatch().getStartTime());
        dto.setProposedStartTime(domain.getMatchStartTime());
        dto.setProposedHomeTeamName(domain.getProposedHomeTeamName());
        dto.setHomeTeamId(domain.getHomeTeam().getId());
        dto.setHomeTeamNames(getAllTeamNames(domain.getHomeTeam()));
        dto.setHomeTeamSimilarityFactor(domain.getHomeSimilarityFactor());
        dto.setAwayTeamId(domain.getAwayTeam().getId());
        dto.setProposedAwayTeamName(domain.getProposedAwayTeamName());
        dto.setAwayTeamNames(getAllTeamNames(domain.getAwayTeam()));
        dto.setAwayTeamSimilarityFactor(domain.getAwaySimilarityFactor());
        dto.setSourceSystem(domain.getSourceSystem());
        return dto;
    }

    @Override
    public ProposedMatchMapping convertToDomain(ProposedMatchMappingDTO dto) {
        throw new UnsupportedOperationException();
    }

    public List<String> getAllTeamNames(Team team) {
        List<String> names = Lists.newArrayList();
        names.add(team.getName());
        names.addAll(team.getAlternativeNames().stream()
                .map(TeamName::getName).collect(Collectors.toList()));
        return names;
    }
}
