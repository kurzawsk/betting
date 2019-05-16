package com.kk.betting.services.matchdatacollection.sourcesystems.betexplorer.dao;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kk.betting.domain.*;
import com.kk.betting.services.common.dao.BookmakerDao;
import com.kk.betting.services.common.dao.MatchDao;
import com.kk.betting.services.common.dao.MatchOddDao;
import com.kk.betting.services.common.dao.TeamDao;
import com.kk.betting.services.common.service.AbstractEntityManagerBasedTest;
import com.kk.betting.services.common.service.WebPageBrowser;
import com.kk.betting.services.common.util.CommonConstants;
import com.kk.betting.services.common.util.DateUtil;
import com.kk.betting.services.common.util.RegexUtil;
import com.kk.betting.services.common.util.TestUtil;
import com.kk.betting.services.matchdatacollection.service.MatchFinishedEventSender;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;


/**
 * Created by KK on 2016-12-28.
 */
public class BetExplorerCollectorIntegrationTest extends AbstractEntityManagerBasedTest {
    private static Log log = LogFactory.getLog(BetExplorerCollectorIntegrationTest.class);

    private static final int NEW_MATCHES_NUMBER_THRESHOLD = 30;
    private static final int YESTERDAY_MATCHES_NUMBER_THRESHOLD = 30;
    private static final int MAX_NO_ATTEMPTS_FIND_NEW_MATCHES = 4;
    private static final int ATTEMPT_INTERVAL = 120;
    private static EnumMap<MatchOdd.Type, Double> ODD_TYPE_EFFICIENCY_THRESHOLD = Maps.newEnumMap(MatchOdd.Type.class);
    private static Map<String, Double> ODD_BY_BOOKMAKER_EFFICIENCY_THRESHOLD = Maps.newHashMap();
    private static EnumMap<MatchResult.Type, Double> MATCH_RESULT_TYPE_RATIO_THRESHOLD = Maps.newEnumMap(MatchResult.Type.class);

    static {
        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODD1, 1.0);
        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDX, 1.0);
        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODD2, 1.0);

        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODD1X, 0.6);
        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDX2, 0.6);
        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODD12, 0.6);

        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDBTSN, 0.4);
        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDBTSY, 0.4);

        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDO05, 0.4);
        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDU05, 0.4);

        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDO15, 0.4);
        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDU15, 0.4);

        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDO25, 0.4);
        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDU25, 0.4);

        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDO35, 0.35);
        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDU35, 0.35);

        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDO45, 0.2);
        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDU45, 0.2);

        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDO55, 0.1);
        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDU55, 0.1);

        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDO65, 0.01);
        ODD_TYPE_EFFICIENCY_THRESHOLD.put(MatchOdd.Type.ODDU65, 0.01);

        MATCH_RESULT_TYPE_RATIO_THRESHOLD.put(MatchResult.Type.UNKNOWN, 0.0);
        MATCH_RESULT_TYPE_RATIO_THRESHOLD.put(MatchResult.Type.NOT_FOUND, 0.15);
        MATCH_RESULT_TYPE_RATIO_THRESHOLD.put(MatchResult.Type.POSTPONED, 0.15);
        MATCH_RESULT_TYPE_RATIO_THRESHOLD.put(MatchResult.Type.CANCELLED, 0.15);

        ODD_BY_BOOKMAKER_EFFICIENCY_THRESHOLD.put(BetExplorerCollector.AVERAGE_NAME, 0.95);
        ODD_BY_BOOKMAKER_EFFICIENCY_THRESHOLD.put(BetExplorerCollector.PINNACLE_SPORTS_NAME, 0.3);
        ODD_BY_BOOKMAKER_EFFICIENCY_THRESHOLD.put(BetExplorerCollector.BET_AT_HOME_NAME, 0.3);
    }

    @Mock
    private MatchFinishedEventSender messageSender;

    @Spy
    private WebPageBrowser webPageBrowser;

    @Spy
    @InjectMocks
    private TeamDao teamDao;

    @Spy
    @InjectMocks
    private MatchOddDao matchOddDao;

    @Spy
    @InjectMocks
    private MatchDao matchDao;

    @Spy
    @InjectMocks
    private BookmakerDao bookmakerDao;

    @InjectMocks
    private BetExplorerCollector betExplorerCollector;

    @Test
    public void testMatchOddsRetrieval() throws InterruptedException, IOException, URISyntaxException {
        startTransaction();
        TestUtil.generateBookmakers(entityManager);

        int attempts = 0;
        do {
            if (attempts > 0) {
                Thread.sleep(TimeUnit.SECONDS.toMillis(ATTEMPT_INTERVAL));
                log.info("Found 0 matches. Attempts left: " + -(MAX_NO_ATTEMPTS_FIND_NEW_MATCHES - attempts));
            }
            betExplorerCollector.findAndInsertNewMatches();
            attempts++;

        }
        while (entityManager.createQuery(" from Match m", Match.class).getResultList().isEmpty() && attempts < MAX_NO_ATTEMPTS_FIND_NEW_MATCHES);


        betExplorerCollector.updateMatchOdds();

        commitTransaction();
        startTransaction();

        List<Match> matches = entityManager.createQuery(" from Match m", Match.class).getResultList();

        Set<MatchOdd> odds = matches.stream().map(m -> {
            entityManager.refresh(m);
            return m;
        }).map(Match::getMatchOdds).flatMap(Collection::stream).collect(Collectors.toSet());
        List<OddEfficiencyResult> efficiencyResults = Lists.newArrayListWithCapacity(bookmakerDao.findAll().size() * MatchOdd.Type.values().length);

        for (Bookmaker bookmaker : bookmakerDao.findAll()) {
            for (MatchOdd.Type oddType : MatchOdd.Type.values()) {
                efficiencyResults.add(new OddEfficiencyResult(bookmaker.getLabel(), oddType,
                        odds.stream().map(mo -> mo.getOdd(oddType)).filter(Objects::nonNull).count() / (double) odds.size()));
            }
        }


        Map<String, Double> oddsRatioByBookmaker = odds.stream().collect(Collectors.groupingBy(mo -> mo.getBookmaker().getName(BetExplorerCollector.SYSTEM_NAME), Collectors.counting())).entrySet().
                stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue() / ((double) matches.size())));

        assertThat(matches.size(), greaterThanOrEqualTo(NEW_MATCHES_NUMBER_THRESHOLD));
        efficiencyResults.forEach(er -> assertThat(" ODD TYPE EFFICIENCY RATIO is too low. Type: " + er.getOddType() + " Bookmaker: " + er.getBookmakerLabel(), er.getScore(),
                greaterThanOrEqualTo(ODD_TYPE_EFFICIENCY_THRESHOLD.get(er.getOddType()))));

        ODD_BY_BOOKMAKER_EFFICIENCY_THRESHOLD.forEach((bookmaker, threshold) -> assertThat(" ODD BY BOOKMAKER EFFICIENCY RATIO is too low. Bookmaker: " + bookmaker, (Double) ObjectUtils.defaultIfNull(oddsRatioByBookmaker.get(bookmaker), 0D), greaterThanOrEqualTo(ODD_BY_BOOKMAKER_EFFICIENCY_THRESHOLD.get(bookmaker))));

        rollbackTransaction();

    }

    @Test
    public void testCheckMatchResultsForYesterday() {
        startTransaction();
        WebPageBrowser browser = new WebPageBrowser();

        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate now = LocalDate.now();
        String[] dayArr = DateUtil.getDateInFormat(yesterday, "dd.MM.yyyy")
                .split("\\.");
        String url = BetExplorerCollector.PATTERN_MATCH_RESULT_URL.replace("#YYYY", dayArr[2]).replace("#MM", dayArr[1]).replace("#DD",
                dayArr[0]);
        String[] lines = browser.doGet(url).split(CommonConstants.NEW_LINE_SYMBOL);
        String regexDate = ".*?tr data-dt=\"(\\d{1,2})\\,(\\d{1,2})\\,(\\d{4}).*?\".*?";
        String regex = ".*?table-matches__time\">(.*?):(.*?)<.*?href=\"(.*?)\">.*?";
        int n = 0;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            String[] matchValues = RegexUtil.extractValuesFromRegex(regex, line);
            if (matchValues.length == 3) {
                String previousLine = lines[i - 1];
                String[] dateValues = RegexUtil.extractValuesFromRegex(regexDate, previousLine);
                if (dateValues.length == 3 && Integer.parseInt(dateValues[0]) == yesterday.getDayOfMonth() && Integer.parseInt(dateValues[1]) == yesterday.getMonthValue()
                        && Integer.parseInt(dateValues[2]) == yesterday.getYear()) {
                    LocalDateTime matchStartTime = DateUtil.getDate(dayArr[0], dayArr[1], dayArr[2], matchValues[0], matchValues[1]);
                    String identifier = matchValues[2].substring(matchValues[2].length() - 9, matchValues[2].length() - 1);
                    entityManager.persist(new Match(identifier, new Team("" + n++), new Team("" + n++), matchStartTime));
                }
            }
        }

        betExplorerCollector.checkMatchResults(LocalDateTime.of(yesterday, LocalTime.MAX));
        betExplorerCollector.checkMatchResults(LocalDateTime.of(now, LocalTime.MAX));

        List<Match> freshMatches = entityManager.createQuery(" from Match m", Match.class).getResultList();

        Map<MatchResult.Type, Set<Match>> matchesByResult = freshMatches.stream().collect(
                Collectors.groupingBy(m -> m.getResult().asType(), Collectors.mapping(Function.identity(), Collectors.toSet())));

        assertThat(freshMatches.size(), greaterThanOrEqualTo(YESTERDAY_MATCHES_NUMBER_THRESHOLD));
        MATCH_RESULT_TYPE_RATIO_THRESHOLD.forEach((resultType, threshold) -> {
            double ratio = ((double) matchesByResult.getOrDefault(resultType, Collections.emptySet()).size()) / freshMatches.size();
            assertThat("Type: " + resultType, ratio, lessThanOrEqualTo(threshold));


        });

        rollbackTransaction();
    }

    private static class OddEfficiencyResult {
        private String bookmakerLabel;
        private MatchOdd.Type oddType;
        private double score;

        public OddEfficiencyResult(String bookmakerLabel, MatchOdd.Type oddType, double score) {
            this.bookmakerLabel = bookmakerLabel;
            this.oddType = oddType;
            this.score = score;
        }

        public String getBookmakerLabel() {
            return bookmakerLabel;
        }

        public MatchOdd.Type getOddType() {
            return oddType;
        }

        public double getScore() {
            return score;
        }


        @Override
        public String toString() {
            return "OddEfficiencyResult{" +
                    "bookmaker=" + bookmakerLabel +
                    ", oddType=" + oddType +
                    ", score=" + score +
                    '}';
        }
    }

}
