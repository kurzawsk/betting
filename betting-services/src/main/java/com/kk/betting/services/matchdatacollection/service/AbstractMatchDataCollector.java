package com.kk.betting.services.matchdatacollection.service;

import com.google.common.collect.Lists;
import com.kk.betting.domain.Match;
import com.kk.betting.dto.MatchFinishEventDTO;
import com.kk.betting.services.common.service.WebPageBrowser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.inject.Inject;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by KK on 2017-02-27.
 */
public abstract class AbstractMatchDataCollector implements MatchDataCollector {

    private static final Log log = LogFactory.getLog(AbstractMatchDataCollector.class);

    @Inject
    protected WebPageBrowser browser;

    @Inject
    protected MatchFinishedEventSender messageSender;

    protected final void distributeMatchFinished(Collection<? extends Match> matches) {
        log.info("Match finish event to be ditributed: " + matches.stream().map(Match::getId).collect(Collectors.toList()));
        if (!matches.isEmpty()) {
            MatchFinishEventDTO[] dtos = matches.stream().map(match -> new MatchFinishEventDTO(match.getId(), match.getResult().toScores())).toArray(MatchFinishEventDTO[]::new);
            messageSender.sendMessage(dtos);
        }
    }

    protected static List<Match> filterAlreadyExistingMatches(List<Match> candidates, List<Match> alreadyTrackedMatches, long maxShiftHours) {
        List<Match> result = Lists.newArrayList();

        for (Match candidate : candidates) {
            boolean foundShift = false;
            boolean isProbablyNewMatch = true;
            for (Match alreadyTrackedMatch : alreadyTrackedMatches) {
                if (candidate.getHomeTeam().equals(alreadyTrackedMatch.getHomeTeam()) && candidate.getAwayTeam().equals(alreadyTrackedMatch.getAwayTeam()) && Objects.equals(candidate.getIdentifier(),alreadyTrackedMatch.getIdentifier())) {
                    isProbablyNewMatch = false;
                    long diff = Math.abs(ChronoUnit.HOURS.between(candidate.getStartTime(), alreadyTrackedMatch.getStartTime()));
                    if (diff > 0 && diff <= maxShiftHours) {
                        log.info("Found match shift: " + diff + " hours. Candidate match: " + candidate + " already tracked Match: " + alreadyTrackedMatch);
                        alreadyTrackedMatch.setStartTime(candidate.getStartTime());
                        result.add(alreadyTrackedMatch);
                        foundShift = true;
                    } else if (diff != 0) {
                        isProbablyNewMatch = true;
                        log.warn("Found match shift greater than threshold: " + diff + " hours. Candidate match: " + candidate + " already tracked Match: " + alreadyTrackedMatch);
                    }
                }else if (candidate.getHomeTeam().equals(alreadyTrackedMatch.getAwayTeam()) && candidate.getAwayTeam().equals(alreadyTrackedMatch.getHomeTeam()) && Objects.equals(candidate.getIdentifier(),alreadyTrackedMatch.getIdentifier())){
                    isProbablyNewMatch = false;
                    log.info("Found match teams switch. Candidate match: " + candidate + " already tracked Match: " + alreadyTrackedMatch);
                    alreadyTrackedMatch.setHomeTeam(candidate.getHomeTeam());
                    alreadyTrackedMatch.setAwayTeam(candidate.getAwayTeam());
                    result.add(alreadyTrackedMatch);
                }
            }
            if (isProbablyNewMatch && !foundShift) {
                result.add(candidate);
            }
        }
        return result;
    }
}
