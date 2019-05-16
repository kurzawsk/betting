package com.kk.betting.services.common.matcher.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.kk.betting.domain.Match;
import com.kk.betting.domain.ProposedMatchMapping;
import com.kk.betting.domain.Team;
import com.kk.betting.domain.TeamName;
import com.kk.betting.services.common.dao.ProposedMatchMappingDao;
import com.kk.betting.services.common.dao.TeamDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static javax.ejb.ConcurrencyManagementType.CONTAINER;
import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;

/**
 * Created by KK on 2017-07-29.
 */

@LocalBean
@Stateless
@ConcurrencyManagement(CONTAINER)
public class MappingService {

    private static final Log log = LogFactory.getLog(MappingService.class);

    @Inject
    private MatchMatcher matcher;

    @Inject
    private TeamDao teamDao;

    @Inject
    private ProposedMatchMappingDao proposedMatchMappingDao;

    @PostConstruct
    @Lock(WRITE)
    public void loadTeams() {
        matcher.loadTeams();
    }

    @Lock(READ)
    public Optional<Match> findMatch(String homeTeamName, String awayTeamName, LocalDateTime startTime) {
        return matcher.findMatch(homeTeamName, awayTeamName, startTime);
    }

    @Lock(READ)
    public List<ProposedMatchMapping> findProposedMatchMappings(String homeTeamName, String awayTeamName, LocalDateTime startTime, String sourceSystem) {
        List<ProposedMatchMapping> proposedMatchMappings = matcher.findSimilarMatches(homeTeamName, awayTeamName, startTime, sourceSystem);
        log.info("Found: " + proposedMatchMappings.size() + " similar matches for provided data: " + homeTeamName + ", " + awayTeamName + " ," + startTime);
        return proposedMatchMappings;
    }

    @Lock(WRITE)
    public List<ProposedMatchMapping> findAndStoreProposedMatchMappings(String homeTeamName, String awayTeamName, LocalDateTime startTime, String sourceSystem) {
        List<ProposedMatchMapping> proposedMatchMappings = matcher.findSimilarMatches(homeTeamName, awayTeamName, startTime, sourceSystem);
        log.info("Found " + proposedMatchMappings.size() + " similar matches for provided data: " + homeTeamName + ", " + awayTeamName + " ," + startTime);
        List<ProposedMatchMapping> filteredProposedMatchMappings = proposedMatchMappings.stream().filter(m -> !isExactMapping(m))
                .filter(m -> !proposedMatchMappingDao.isMappingAlreadyPresent(m.getHomeTeam().getId(), m.getAwayTeam().getId(), m.getProposedHomeTeamName(), m.getProposedAwayTeamName())).collect(Collectors.toList());
        proposedMatchMappingDao.persist(filteredProposedMatchMappings);
        return filteredProposedMatchMappings;
    }

    @Lock(WRITE)
    public boolean acceptProposedMatchMapping(Long proposedMatchMappingId) {
        ProposedMatchMapping proposedMatchMapping = proposedMatchMappingDao.findById(proposedMatchMappingId);
        if (canProposedMatchMappingBeAcceptedOrRejected(proposedMatchMapping)) {
            acceptProposedMatchMapping(proposedMatchMapping);
            teamDao.persist(getTeamsToUpdate(Collections.singletonList(proposedMatchMapping)));
            matcher.loadTeams();
            return true;
        }
        return false;
    }

    @Lock(WRITE)
    public boolean rejectProposedMatchMapping(Long proposedMatchMappingId) {
        ProposedMatchMapping proposedMatchMapping = proposedMatchMappingDao.findById(proposedMatchMappingId);
        if (canProposedMatchMappingBeAcceptedOrRejected(proposedMatchMapping)) {
            rejectProposedMatchMapping(proposedMatchMapping);
            return true;
        }
        return false;
    }

    @Lock(WRITE)
    public boolean processProposedMatchMappings(List<Long> idsToAccept, List<Long> idsToReject) {
        List<ProposedMatchMapping> proposedMatchMappingToAccept = idsToAccept.stream().map(id -> proposedMatchMappingDao.findById(id)).collect(Collectors.toList());
        List<ProposedMatchMapping> proposedMatchMappingToReject = idsToReject.stream().map(id -> proposedMatchMappingDao.findById(id)).collect(Collectors.toList());
        long incorrectMappingCount = proposedMatchMappingToAccept.stream().filter(p -> !canProposedMatchMappingBeAcceptedOrRejected(p)).count() +
                proposedMatchMappingToReject.stream().filter(p -> !canProposedMatchMappingBeAcceptedOrRejected(p)).count();
        if (incorrectMappingCount > 0) {
            return false;
        }
        proposedMatchMappingToReject.stream().forEach(this::rejectProposedMatchMapping);
        proposedMatchMappingToAccept.stream().forEach(this::acceptProposedMatchMapping);
        teamDao.persist(getTeamsToUpdate(proposedMatchMappingToAccept));
        matcher.loadTeams();
        return true;
    }

    private void rejectProposedMatchMapping(ProposedMatchMapping proposedMatchMapping) {
        proposedMatchMapping.setStatus(ProposedMatchMapping.Status.REJECTED);
        proposedMatchMappingDao.persist(proposedMatchMapping);
    }

    private void acceptProposedMatchMapping(ProposedMatchMapping proposedMatchMapping) {
        proposedMatchMapping.setStatus(ProposedMatchMapping.Status.ACCEPTED);
        proposedMatchMappingDao.persist(proposedMatchMapping);
    }

    private boolean canProposedMatchMappingBeAcceptedOrRejected(ProposedMatchMapping proposedMatchMapping) {
        return proposedMatchMapping != null && proposedMatchMapping.getStatus() == ProposedMatchMapping.Status.NEW;
    }

    private List<Team> getTeamsToUpdate(List<ProposedMatchMapping> proposedMatchMappings) {
        List<TeamName> newTeamNames = Lists.newArrayList();
        for (ProposedMatchMapping proposedMatchMapping : proposedMatchMappings) {
            String newHomeTeamName = proposedMatchMapping.getProposedHomeTeamName();
            String newAwayTeamName = proposedMatchMapping.getProposedAwayTeamName();

            Team homeTeam = proposedMatchMapping.getHomeTeam();
            Team awayTeam = proposedMatchMapping.getAwayTeam();

            if (!isTeamAlreadyHavingName(homeTeam, newHomeTeamName)) {
                newTeamNames.add(new TeamName(homeTeam, newHomeTeamName));
            }
            if (!isTeamAlreadyHavingName(awayTeam, newAwayTeamName)) {
                newTeamNames.add(new TeamName(awayTeam, newAwayTeamName));
            }
        }

        return newTeamNames.stream().collect(Collectors.groupingBy(TeamName::getTeam, Collectors.toSet()))
                .entrySet().stream().map(e -> {
                    e.getKey().getAlternativeNames().addAll(e.getValue());
                    return e.getKey();
                }).collect(Collectors.toList());
    }

    private boolean isTeamAlreadyHavingName(Team team, String name) {
        Set<String> names = Sets.newHashSet();
        names.add(team.getName());
        names.addAll(team.getAlternativeNames().stream().map(TeamName::getName).collect(Collectors.toSet()));
        return names.contains(name);
    }

    private boolean isExactMapping(ProposedMatchMapping proposedMatchMapping) {
        return 1D == proposedMatchMapping.getAwaySimilarityFactor() * proposedMatchMapping.getHomeSimilarityFactor();
    }


}
