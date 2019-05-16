package com.kk.betting.services.bettorhandling.typersi.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kk.betting.services.bettorhandling.typersi.dto.TypersiBettorHistoryEntryDTO;
import com.kk.betting.services.bettorhandling.typersi.dto.TypersiMatchBetProposalDTO;
import com.kk.betting.services.bettorhandling.typersi.service.TypersiBetProposalParser;
import com.kk.betting.services.bettorhandling.typersi.service.TypersiHistoryParser;
import com.kk.betting.services.common.service.WebPageBrowser;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by KK on 2017-08-02.
 */
@Named
public class TypersiRawDao {

    public static final String MAIN_URL = "http://typersi.pl/";
    public static final String OTHERS_REMAINING_URL = MAIN_URL + "pozostali,remainder.html";
    public static final String TOMORROW_URL = MAIN_URL + "jutro,tomorrow.html";
    public static final String STATISTICS_URL = MAIN_URL + "getstats,Stats.html";
    public static final String BETTOR_CATEGORY_SPLITTER = "trtop";
    public static final String ODDS_LINE_MARKER = "toolTyp";
    public static final String TOP5_MARKER = "Typy najlepszej piątki z rankingu typerów";
    public static final String MOST_EFFICIENT_MARKER = "Typy najskuteczniejszych typerów";
    public static final String STATS_MONTH_PARAMETER = "mon";
    public static final String STATS_YEAR_PARAMETER = "year";

    @Inject
    private WebPageBrowser browser;

    @Inject
    private TypersiBetProposalParser typersiBetProposalParser;

    @Inject
    private TypersiHistoryParser typersiHistoryParser;

    public List<TypersiMatchBetProposalDTO> getTodaysTop5BettorsOdds() {
        String lineOfInterest = getLineOfInterest(MAIN_URL, BettorCategory.TOP5);
        return typersiBetProposalParser.parse(lineOfInterest);
    }

    public List<TypersiMatchBetProposalDTO> getTomorrowsTop5BettorsOdds() {
        String lineOfInterest = getLineOfInterest(TOMORROW_URL, BettorCategory.TOP5);
        return typersiBetProposalParser.parse(lineOfInterest);
    }

    public List<TypersiMatchBetProposalDTO> getTodaysMostEfficientBettorsOdds() {
        String lineOfInterest = getLineOfInterest(MAIN_URL, BettorCategory.MOST_EFFICIENT);
        return typersiBetProposalParser.parse(lineOfInterest);
    }

    public List<TypersiMatchBetProposalDTO> getTomorrowsMostEfficientBettorsOdds() {
        String lineOfInterest = getLineOfInterest(TOMORROW_URL, BettorCategory.MOST_EFFICIENT);
        return typersiBetProposalParser.parse(lineOfInterest);
    }

    public List<TypersiMatchBetProposalDTO> getTodaysBettorsOdds(List<String> bettorNames) {
        return getBettordOdds(MAIN_URL, bettorNames);
    }

    public List<TypersiMatchBetProposalDTO> getTomorrowsBettorsOdds(List<String> bettorNames) {
        return getBettordOdds(TOMORROW_URL, bettorNames);
    }

    public List<TypersiBettorHistoryEntryDTO> getBettorsStatistics(YearMonth period) {
        Map<String, Object> params = Maps.newHashMap();
        params.put(STATS_YEAR_PARAMETER, period.getYear());
        params.put(STATS_MONTH_PARAMETER, period.getMonthValue());

        return typersiHistoryParser.parse( browser.doPostForm(STATISTICS_URL, params));

    }

    private List<TypersiMatchBetProposalDTO> getBettordOdds(String url, List<String> bettorNames) {
        List<TypersiMatchBetProposalDTO> result = Lists.newArrayList();
        result.addAll(typersiBetProposalParser.parse(getLineOfInterest(url, BettorCategory.TOP5)));
        result.addAll(typersiBetProposalParser.parse(getLineOfInterest(url, BettorCategory.MOST_EFFICIENT)));
        result.addAll(typersiBetProposalParser.parse(getLineOfInterest(url, BettorCategory.OTHERS)));
        result.addAll(typersiBetProposalParser.parse(getLineOfInterest(OTHERS_REMAINING_URL, BettorCategory.OTHERS_REMAINING)));
        return result.stream().filter(t -> bettorNames.stream()
                .filter(bettorName -> t.getBettor().equals(bettorName)).findAny().isPresent()).collect(Collectors.toList());

    }

    private String getLineOfInterest(String url, BettorCategory bettorCategory) {
        String content = browser.doGet(url);
        int decreaseIdx = 0;
        if (!content.contains(bettorCategory.getCategoryMarker())) return StringUtils.EMPTY;
        if (!content.contains(TypersiRawDao.TOP5_MARKER)) decreaseIdx++;
        if (!content.contains(TypersiRawDao.MOST_EFFICIENT_MARKER)) decreaseIdx++;
        String[] types = content.split(BETTOR_CATEGORY_SPLITTER);
        List<String> list = Stream.of(types).filter(t -> t.contains(ODDS_LINE_MARKER)).collect(Collectors.toList());

        return list.isEmpty() ? StringUtils.EMPTY : list.get(Math.max(bettorCategory.getNumber() - decreaseIdx, 0));
    }

    private enum BettorCategory {
        TOP5 {
            public int getNumber() {
                return 0;
            }

            public String getCategoryMarker() {
                return TypersiRawDao.TOP5_MARKER;
            }
        }, MOST_EFFICIENT {
            public int getNumber() {
                return 1;
            }

            public String getCategoryMarker() {
                return TypersiRawDao.MOST_EFFICIENT_MARKER;
            }
        }, OTHERS {
            public int getNumber() {
                return 2;
            }

            public String getCategoryMarker() {
                return StringUtils.EMPTY;
            }
        },
        OTHERS_REMAINING {
            public int getNumber() {
                return 0;
            }

            public String getCategoryMarker() {
                return StringUtils.EMPTY;
            }
        };

        public abstract int getNumber();

        public abstract String getCategoryMarker();
    }
}
