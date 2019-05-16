package com.kk.betting.services.bettorhandling.typersi.service;

import com.google.common.collect.Maps;
import com.kk.betting.domain.TypersiHistoryEntry;
import com.kk.betting.services.bettorhandling.typersi.dao.TypersiHistoryEntryDao;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by KK on 2017-09-02.
 */
public class TypersiHistoryAnalyzer {

    private static final int MIN_NUMBER_OF_BETS_PER_MONTH = 3;
    private static final int MIN_MONTHS_OF_PRESENCE = 10;

    @Inject
    private TypersiHistoryEntryDao typersiHistoryEntryDao;

    public EnumMap<TypersiBettorCategory, List<String>> getTypersiBettorsByCategory() {
        List<? extends TypersiHistoryEntry> relevantEntries = typersiHistoryEntryDao.findAll().stream()
                .filter(the -> (the.getLostBets() + the.getWonBets()) > MIN_NUMBER_OF_BETS_PER_MONTH).collect(Collectors.toList());

        Map<String, List<BettorStats>> bettorStats = relevantEntries.stream()
                .collect(Collectors.groupingBy(TypersiHistoryEntry::getBettor, Collectors.toList())).entrySet().stream().map(e -> e.getValue().stream()
                        .map(en -> new AbstractMap.SimpleEntry<>(e.getKey(), new BettorStats((double) en.getWonBets() / (en.getLostBets() + en.getWonBets()), en.getProfit()))))
                .flatMap(Function.identity()).collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey, Collectors.toList()))
                .entrySet().stream().filter(e->e.getValue().size() > MIN_MONTHS_OF_PRESENCE).collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream().map(AbstractMap.SimpleEntry::getValue).collect(Collectors.toList())));

        List<GrouppedBettorStats> groupedBettorStats = bettorStats.entrySet().stream().map(e -> {
            BigDecimal sumProfit = e.getValue().stream().map(BettorStats::getProfit).reduce(BigDecimal::add).get();
            double avgEfficiency = e.getValue().stream().mapToDouble(BettorStats::getEfficiency).average().getAsDouble();
            return new GrouppedBettorStats(e.getKey(), avgEfficiency, sumProfit);
        }).collect(Collectors.toList());

        EnumMap<TypersiBettorCategory, List<String>> topBettorsByCategory = Maps.newEnumMap(TypersiBettorCategory.class);
        topBettorsByCategory.put(TypersiBettorCategory.PROFIT, groupedBettorStats.stream().sorted((g1, g2) -> g2.getProfit()
                .compareTo(g1.getProfit())).limit(TypersiBettorCategory.PROFIT.getLimit()).map(GrouppedBettorStats::getBettor).collect(Collectors.toList()));
        topBettorsByCategory.put(TypersiBettorCategory.EFFICIENCY, groupedBettorStats.stream().sorted((g1, g2) -> Double.compare(g2.getEfficiency(), g1.getEfficiency()))
                .limit(TypersiBettorCategory.EFFICIENCY.getLimit()).map(GrouppedBettorStats::getBettor).collect(Collectors.toList()));

        return topBettorsByCategory;
    }


    private static class BettorStats {
        private double efficiency;
        private BigDecimal profit;

        public BettorStats(double efficiency, BigDecimal profit) {
            this.efficiency = efficiency;
            this.profit = profit;
        }

        public double getEfficiency() {
            return efficiency;
        }

        public BigDecimal getProfit() {
            return profit;
        }

    }

    private static class GrouppedBettorStats extends BettorStats {
        private String bettor;

        public GrouppedBettorStats(String bettor, double efficiency, BigDecimal profit) {
            super(efficiency, profit);
            this.bettor = bettor;
        }

        public String getBettor() {
            return bettor;
        }
    }
}
