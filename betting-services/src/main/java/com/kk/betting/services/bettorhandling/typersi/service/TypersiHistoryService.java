package com.kk.betting.services.bettorhandling.typersi.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.kk.betting.domain.TypersiHistoryEntry;
import com.kk.betting.services.bettorhandling.typersi.dao.TypersiRawDao;
import com.kk.betting.services.bettorhandling.typersi.dao.TypersiHistoryEntryDao;
import com.kk.betting.services.bettorhandling.typersi.dto.TypersiBettorHistoryEntryDTO;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

/**
 * Created by KK on 2017-08-30.
 */
@Named
public class TypersiHistoryService {

    @Inject
    private TypersiHistoryEntryDao typersiHistoryEntryDao;

    @Inject
    private TypersiRawDao typersiRawDao;

    public void updateTypersiHistory(long months){
        List<TypersiHistoryEntry> typersiHistoryEntries = Lists.newArrayList();
        YearMonth start = YearMonth.now().minusMonths(1L);
        Multimap<YearMonth,TypersiBettorHistoryEntryDTO> statsByMonth = ArrayListMultimap.create();

        for (long i = months ; i >= 0; i--){
            YearMonth month = start.minusMonths(i);
            typersiRawDao.getBettorsStatistics(month).stream().forEach(dto->statsByMonth.put(month,dto));
        }

        statsByMonth.asMap().forEach((month,entries)-> entries.stream().forEach(entry->{
            TypersiHistoryEntry typersiHistoryEntry = new TypersiHistoryEntry();
            typersiHistoryEntry.setMonth(month);
            typersiHistoryEntry.setBettor(entry.getBettor());
            typersiHistoryEntry.setAvgOdd(new BigDecimal(entry.getAvgOdd()));
            typersiHistoryEntry.setProfit(new BigDecimal(entry.getProfit()));
            typersiHistoryEntry.setLostBets(Integer.parseInt(entry.getLostBets()));
            typersiHistoryEntry.setWonBets(Integer.parseInt(entry.getWonBets()));

            typersiHistoryEntries.add(typersiHistoryEntry);
        }));

        typersiHistoryEntryDao.deleteAll();
        typersiHistoryEntryDao.persist(typersiHistoryEntries);

    }
}
