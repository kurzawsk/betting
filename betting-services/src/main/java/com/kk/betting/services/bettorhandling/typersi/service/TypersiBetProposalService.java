package com.kk.betting.services.bettorhandling.typersi.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.kk.betting.domain.Match;
import com.kk.betting.domain.MatchOdd;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.bettorhandling.typersi.dao.TypersiRawDao;
import com.kk.betting.services.bettorhandling.typersi.dto.TypersiMatchBetProposalDTO;
import com.kk.betting.services.common.matcher.service.MappingService;
import com.kk.betting.services.common.util.RawMatchDataUtil;
import com.kk.betting.services.common.util.RegexUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by KK on 2017-08-02.
 */
@Named
public class TypersiBetProposalService {


    private static final Log log = LogFactory.getLog(TypersiBetProposalService.class);

    public static final String SOURCE_NAME_PREFIX = "TYPERSI.";
    private static final int MAX_ALLOWED_MATCH_START_TIME_DIFFERENCE = 1500;
    private static final List<String> TEAM_NAMES_SPLITTERS = ImmutableList.of(" - ", " – ", " vs. ", " vs ", "-", "–");
    private static final List<String> TIME_FORMATS = ImmutableList.of("(\\d{2}):(\\d{2})", "(\\d{2})\\.(\\d{2})","(\\d{2})\\,(\\d{2})");

    private static final String SOURCE_NAME_TOP5 = "TOP5";
    private static final String SOURCE_NAME_MOST_EFFICIENT = "MOST_EFFICIENT";

    @Inject
    private MappingService mappingService;
    @Inject
    private TypersiRawDao typersiDao;

    public List<MatchBetProposalRawDTO> getTodaysTop5BettorsOdds() {
        List<TypersiMatchBetProposalDTO> matchProposalDTOs = typersiDao.getTodaysTop5BettorsOdds();
        return matchProposalDTOs.stream().map(dto -> processMatchProposalDTO(dto, StartDay.TODAY, SOURCE_NAME_PREFIX + SOURCE_NAME_TOP5))
                .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

    public List<MatchBetProposalRawDTO> getTomorrowsTop5BettorsOdds() {
        List<TypersiMatchBetProposalDTO> matchProposalDTOs = typersiDao.getTomorrowsTop5BettorsOdds();
        return matchProposalDTOs.stream().map(dto -> processMatchProposalDTO(dto, StartDay.TOMORROW, SOURCE_NAME_PREFIX + SOURCE_NAME_TOP5))
                .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

    public List<MatchBetProposalRawDTO> getTodaysMostEfficientBettorsOdds() {
        List<TypersiMatchBetProposalDTO> matchProposalDTOs = typersiDao.getTodaysMostEfficientBettorsOdds();
        return matchProposalDTOs.stream().map(dto -> processMatchProposalDTO(dto, StartDay.TODAY, SOURCE_NAME_PREFIX + SOURCE_NAME_MOST_EFFICIENT))
                .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

    public List<MatchBetProposalRawDTO> getTomorrowsMostEfficientBettorsOdds() {
        List<TypersiMatchBetProposalDTO> matchProposalDTOs = typersiDao.getTomorrowsMostEfficientBettorsOdds();
        return matchProposalDTOs.stream().map(dto -> processMatchProposalDTO(dto, StartDay.TOMORROW, SOURCE_NAME_PREFIX + SOURCE_NAME_MOST_EFFICIENT))
                .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

    public List<MatchBetProposalRawDTO> getTodaysBettorOdds(List<String> bettorNames) {
        List<TypersiMatchBetProposalDTO> matchProposalDTOs = typersiDao.getTodaysBettorsOdds(bettorNames);
        return matchProposalDTOs.stream().map(dto -> processMatchProposalDTO(dto, StartDay.TODAY, SOURCE_NAME_PREFIX + dto.getBettor()))
                .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

    public List<MatchBetProposalRawDTO> getTomorrowsBettorOdds(List<String> bettorNames) {
        List<TypersiMatchBetProposalDTO> matchProposalDTOs = typersiDao.getTomorrowsBettorsOdds(bettorNames);
        return matchProposalDTOs.stream().map(dto -> processMatchProposalDTO(dto, StartDay.TOMORROW, SOURCE_NAME_PREFIX + dto.getBettor()))
                .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

    private Map.Entry<String, String> extractTimeNames(String teamNames) {
        for (String splitter : TEAM_NAMES_SPLITTERS) {
            String[] arr = teamNames.split(splitter);
            if (arr.length == 2) return Maps.immutableEntry(arr[0].trim(), arr[1].trim());
        }
        return null;
    }

    private LocalDateTime extractMatchStartTime(String time, StartDay startDay) {
        LocalDateTime day = startDay.get();
        for (String timeFormat : TIME_FORMATS) {
            String[] timeSplit = RegexUtil.extractValuesFromRegex(timeFormat, time);
            if (timeSplit.length == 2) {
                return LocalDateTime.from(day).withHour(Integer.parseInt(timeSplit[0])).withMinute(Integer.parseInt(timeSplit[1]));
            }
        }
        return null;
    }

    private enum StartDay {
        TODAY {
            public LocalDateTime get() {
                return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
            }
        }, TOMORROW {
            public LocalDateTime get() {
                return TODAY.get().plusDays(1L);
            }
        };

        public abstract LocalDateTime get();
    }

    private Optional<MatchBetProposalRawDTO> processMatchProposalDTO(TypersiMatchBetProposalDTO matchProposalDTO, StartDay startDay, String sourceName) {
        log.info("--------------------------------------------------");
        Map.Entry<String, String> teamNames = extractTimeNames(matchProposalDTO.getMatch());
        LocalDateTime startTime = extractMatchStartTime(matchProposalDTO.getTime(), startDay);
        MatchOdd.Type proposedResult = RawMatchDataUtil.parseRawProposedMatchResult(matchProposalDTO.getProposedResult());

        if (Objects.nonNull(teamNames) && Objects.nonNull(startTime) && Objects.nonNull(proposedResult)) {
            Optional<Match> match = mappingService.findMatch(teamNames.getKey(), teamNames.getValue(), null);

            if (match.isPresent()) {
                if (ChronoUnit.MINUTES.between(match.get().getStartTime(), startTime) > MAX_ALLOWED_MATCH_START_TIME_DIFFERENCE) {
                    throw new IllegalStateException("Found match: " + match + " start time does not reflected start time from typersi: " + startTime);
                }

                log.warn("Match has been found for proposal: " + matchProposalDTO);
                return Optional.of(
                        MatchBetProposalRawDTO.builder().setMatchId(match.get().getId()).setOdd(new BigDecimal(matchProposalDTO.getOdd())).
                                setExpectedMatchResult(proposedResult.toString()).setProposalSource(sourceName).build());
            } else {
                log.warn("Match has NOT been found: " + matchProposalDTO + ". Proceeding to a finding similar matches.");
                mappingService.findAndStoreProposedMatchMappings(teamNames.getKey(), teamNames.getValue(), startTime, sourceName);
            }
        } else {
            log.warn("Couldn't extract match values for: " + matchProposalDTO + ". teamNames = " + teamNames + ", startTime = " + startTime + ", " + proposedResult);
        }
        return Optional.empty();
    }
}
