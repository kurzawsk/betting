package com.kk.betting.services.bettorhandling.typersi.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.bettorhandling.common.service.BettingProposalSourceLogic;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by KK on 2017-08-03.
 */

@Named
public class TypersiLogic extends BettingProposalSourceLogic {

    private static final Log log = LogFactory.getLog(TypersiBetProposalService.class);
    private static final int MIN_HOURS_TO_START_MATCH = 1;

    @Inject
    private TypersiBetProposalService typersiBetProposalService;

    @Inject
    private TypersiHistoryAnalyzer typersiHistoryAnalyzer;

    @Override
    protected List<MatchBetProposalRawDTO> prepareBettingProposals() {

        EnumMap<TypersiBettorCategory, List<String>> bettorsByCategory = typersiHistoryAnalyzer.getTypersiBettorsByCategory();

        List<MatchBetProposalRawDTO> allMatchBetProposalRawDTOs = Lists.newArrayList();
        allMatchBetProposalRawDTOs.addAll(getCategorizedRawProposalsForFollowedBettors(bettorsByCategory));

        allMatchBetProposalRawDTOs.addAll(typersiBetProposalService.getTodaysMostEfficientBettorsOdds());
        allMatchBetProposalRawDTOs.addAll(typersiBetProposalService.getTomorrowsMostEfficientBettorsOdds());

        allMatchBetProposalRawDTOs.addAll(typersiBetProposalService.getTodaysTop5BettorsOdds());
        allMatchBetProposalRawDTOs.addAll(typersiBetProposalService.getTomorrowsTop5BettorsOdds());

        return mergeTheSameMatches(filterByStartTime(allMatchBetProposalRawDTOs));
    }

    private List<MatchBetProposalRawDTO> getCategorizedRawProposalsForFollowedBettors(EnumMap<TypersiBettorCategory, List<String>> bettorsByCategory) {
        List<String> bettorsToFollow = bettorsByCategory.entrySet().stream().map(Map.Entry::getValue).flatMap(List::stream).distinct().collect(Collectors.toList());
        Map<String, List<String>> bettorsByExactCategory = getBettorsByExactCategoryName(bettorsByCategory);
        List<MatchBetProposalRawDTO> matchBetProposalRawDTOFollowedPlayers = Lists.newArrayList();

        matchBetProposalRawDTOFollowedPlayers.addAll(typersiBetProposalService.getTodaysBettorOdds(bettorsToFollow));
        matchBetProposalRawDTOFollowedPlayers.addAll(typersiBetProposalService.getTomorrowsBettorOdds(bettorsToFollow));

        return matchBetProposalRawDTOFollowedPlayers.stream().map(dto -> bettorsByExactCategory.get(dto.getProposalSource()).stream()
                .map(category -> MatchBetProposalRawDTO.builder().setMatchId(dto.getMatchId()).setExpectedMatchResult(dto.getExpectedMatchResult())
                        .setOdd(dto.getOdd()).setProposalSource(category).build())).flatMap(Function.identity()).collect(Collectors.toList());
    }


    private Map<String, List<String>> getBettorsByExactCategoryName(EnumMap<TypersiBettorCategory, List<String>> bettorsByCategory) {
        int step = 10;
        Map<String, List<String>> bettorsByExactCategory = Maps.newHashMap();

        for (Map.Entry<TypersiBettorCategory, List<String>> entry : bettorsByCategory.entrySet()) {
            List<List<String>> chunks = Lists.partition(entry.getValue(), step);

            for (int i = 1; i <= chunks.size(); i++) {
                String categoryName = entry.getKey().toString() + "_" + i * step;
                if (!bettorsByExactCategory.containsKey(categoryName)) {
                    bettorsByExactCategory.put(categoryName, Lists.newArrayList());
                }
                bettorsByExactCategory.get(categoryName).addAll(chunks.subList(0, i).stream().flatMap(List::stream).collect(Collectors.toList()));
            }
        }

        Map<String, List<String>> exactCategoryByBettor = Maps.newHashMap();
        for (Map.Entry<String, List<String>> entry : bettorsByExactCategory.entrySet()) {
            for (String bettor : entry.getValue()) {
                if (!exactCategoryByBettor.containsKey(TypersiBetProposalService.SOURCE_NAME_PREFIX + bettor)) {
                    exactCategoryByBettor.put(TypersiBetProposalService.SOURCE_NAME_PREFIX + bettor, Lists.newArrayList());
                }
                exactCategoryByBettor.get(TypersiBetProposalService.SOURCE_NAME_PREFIX + bettor).add(TypersiBetProposalService.SOURCE_NAME_PREFIX+entry.getKey());
            }
        }

        return exactCategoryByBettor;
    }

    private List<MatchBetProposalRawDTO> mergeTheSameMatches(List<MatchBetProposalRawDTO> input) {
        return input.stream()
                .collect(Collectors.groupingBy(MatchBetProposalRawDTO::getMatchId, Collectors.toList()))
                .entrySet().stream().map(e -> mergeSingleMatchDTOs(e.getValue())).flatMap(Collection::stream).collect(Collectors.toList());

    }

    private List<MatchBetProposalRawDTO> filterByStartTime(List<MatchBetProposalRawDTO> input) {
        LocalDateTime now = LocalDateTime.now();
        return input.stream().filter(dto -> ChronoUnit.HOURS.between(now, matchDao.findById(dto.getMatchId()).getStartTime()) > MIN_HOURS_TO_START_MATCH)
                .collect(Collectors.toList());
    }

    private List<MatchBetProposalRawDTO> mergeSingleMatchDTOs(List<MatchBetProposalRawDTO> input) {
        List<MatchBetProposalRawDTO> result = Lists.newArrayList();
        Map<String, List<MatchBetProposalRawDTO>> proposalsByExpectedResult = input.stream().collect(Collectors.groupingBy(MatchBetProposalRawDTO::getExpectedMatchResult, Collectors.toList()));

        for (Map.Entry<String, List<MatchBetProposalRawDTO>> e : proposalsByExpectedResult.entrySet()) {
            String groupedProposalSource = e.getValue().stream().map(MatchBetProposalRawDTO::getProposalSource).collect(Collectors.joining(","));
            MatchBetProposalRawDTO mergedDTO = MatchBetProposalRawDTO.builder().setMatchId(e.getValue().get(0).getMatchId())
                    .setExpectedMatchResult(e.getValue().get(0).getExpectedMatchResult())
                    .setOdd(e.getValue().get(0).getOdd())
                    .setProposalSource(groupedProposalSource).build();
            result.add(mergedDTO);
        }
        return result;
    }

}
