package com.kk.betting.services.matchdatacollection.sourcesystems.betexplorer.dao;

import com.google.common.collect.*;
import com.kk.betting.domain.*;
import com.kk.betting.services.common.dao.BookmakerDao;
import com.kk.betting.services.common.dao.MatchDao;
import com.kk.betting.services.common.dao.MatchOddDao;
import com.kk.betting.services.common.dao.TeamDao;
import com.kk.betting.services.common.service.AbstractEntityManagerBasedTest;
import com.kk.betting.services.common.service.WebPageBrowser;
import com.kk.betting.services.common.util.CommonConstants;
import com.kk.betting.services.common.util.DateUtil;
import com.kk.betting.services.matchdatacollection.service.MatchFinishedEventSender;
import com.kk.betting.services.matchdatacollection.sourcesystems.betexplorer.service.*;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.kk.betting.services.common.util.TestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class BetExplorerCollectorTest extends AbstractEntityManagerBasedTest {

    private static final String patternMatchResultPenalties = "<td class=\"table-matches__tt\"><span class=\"table-matches__time\">14:30</span><a href=\"/soccer/country/league/#MATCH_ID/\">Team1 - Team2</a></td>\n<td class=\"livebet\">&nbsp;</td><\ntd class=\"table-matches__result\"><strong>#RESULT <span title=\"After Penalties\">PEN.</span></strong></td>\n<td class=\"table-matches__partial\" colspan=\"3\">(#PARTIAL_RESULTS)</td>\n";
    private static final String patternMatchResultExtraTime = "<td class=\"table-matches__tt\"><span class=\"table-matches__time\">14:30</span><a href=\"/soccer/country/league/#MATCH_ID/\">Team1 - Team2</a></td>\n<td class=\"livebet\">&nbsp;</td><\ntd class=\"table-matches__result\"><strong>#RESULT <span title=\"After Extra Time\">ET</span></strong></td>\n<td class=\"table-matches__partial\" colspan=\"3\">(#PARTIAL_RESULTS)</td>\n";
    private static final String patternMatchResultPostponed = "<td class=\"table-matches__tt\"><span class=\"table-matches__time\">14:30</span><a href=\"/soccer/country/league/#MATCH_ID/\">Team1 - Team2</a></td>\n<td class=\"livebet\">&nbsp;</td><\ntd class=\"table-matches__result\"><strong><span title=\"Postponed\">POSTP.</span></strong></td>\n<td class=\"table-matches__partial\" colspan=\"3\">&nbsp;</td>\n";
    private static final String patternMatchResultCancelled = "<td class=\"table-matches__tt\"><span class=\"table-matches__time\">14:30</span><a href=\"/soccer/country/league/#MATCH_ID/\">Team1 - Team2</a></td>\n<td class=\"livebet\">&nbsp;</td><\ntd class=\"table-matches__result\"><strong>0:2<strong><span title=\"Awarded\">AWA.</span></strong></td>\n<td class=\"table-matches__partial\" colspan=\"3\">&nbsp;</td>\n";
    private static final String patternMatchResult = "<td class=\"table-matches__tt\"><span class=\"table-matches__time\">14:30</span><a href=\"/soccer/country/league/#MATCH_ID/\">Team1 - Team2</a></td>\n<td class=\"livebet\">&nbsp;</td><\ntd class=\"table-matches__result\"><strong>#RESULT</strong></td>\n<td class=\"table-matches__partial\" colspan=\"3\">(0:0, 0:4)</td>\n";

    @Mock
    private MatchFinishedEventSender messageSender;

    @Mock
    private WebPageBrowser webPageBrowser;

    @Spy
    private Odds1X2Parser odds1X2Parser;

    @Spy
    private OddsDCParser oddsDCParser;

    @Spy
    private OddsOUParser oddsOUParser;

    @Spy
    private OddsBTSParser oddsBTSParser;

    @Spy
    private MatchResultParser matchResultParser;

    @Spy
    private NewMatchParser newMatchParser;

    @Spy
    @InjectMocks
    private MatchDao matchDao;

    @Spy
    @InjectMocks
    private TeamDao teamDao;

    @Spy
    @InjectMocks
    private MatchOddDao matchOddDao;

    @Spy
    @InjectMocks
    private BookmakerDao bookmakerDao;

    @InjectMocks
    private BetExplorerCollector betExplorerCollector = new BetExplorerCollector();

    @Test
    public void testFindAndInsertNewMatches() {

        startTransaction();
        int expectedNoMatches = 8;
        Map<String, String[]> newMatchesRaw = Maps.newHashMap();

        Random random = new Random();
        for (int i = 0; i < expectedNoMatches; i++) {
            double hours = (CommonConstants.DAYS_INTERVAL_BEFORE_MATCH_TO_ADD_UPPER_BOUNDARY + (random.nextDouble() *
                    (CommonConstants.DAYS_INTERVAL_BEFORE_MATCH_TO_ADD_UPPER_BOUNDARY * 0.98 - CommonConstants.DAYS_INTERVAL_BEFORE_MATCH_TO_ADD_UPPER_BOUNDARY))) * 24;

            String[] dateStr = getTimeByHoursFromNow(hours);
            newMatchesRaw.put("testMatch_" + i, (String[]) ArrayUtils.addAll(new String[]{"testhomeTeam_" + i + "_" + i, "testawayTeam_" + i + "_" + i}, dateStr));
        }

        when(newMatchParser.parse(anyString())).thenReturn(newMatchesRaw);
        betExplorerCollector.findAndInsertNewMatches();

        List<Match> matches = entityManager.createQuery(" from Match m", Match.class).getResultList();
        List<Team> teams = entityManager.createQuery(" from Team t", Team.class).getResultList();
        assertEquals("Incorrect number of added matches!", expectedNoMatches, matches.size());
        assertEquals("Incorrect number of added teams!", expectedNoMatches * 2, teams.size());

        rollbackTransaction();
    }


    @Test
    public void testFindAndInsertNewMatchesWithSwitch() {

        startTransaction();
        int expectedNoMatches = 8;
        Map<String, String[]> newMatchesRaw = Maps.newHashMap();
        List<Match> alreadyTrackedMatches = Lists.newArrayListWithCapacity(expectedNoMatches / 2);
        Map<String, LocalDateTime> expectedStartTimes = Maps.newHashMap();

        //Random random = new Random();
        for (int i = 0; i < expectedNoMatches; i++) {
            int hoursToStart = (int) ((CommonConstants.DAYS_INTERVAL_BEFORE_MATCH_TO_ADD_LOWER_BOUNDARY + (0.5 *
                    (CommonConstants.DAYS_INTERVAL_BEFORE_MATCH_TO_ADD_UPPER_BOUNDARY - CommonConstants.DAYS_INTERVAL_BEFORE_MATCH_TO_ADD_LOWER_BOUNDARY))) * 24);

            String homeTeamName = "testhomeTeam_" + i + "_" + i;
            String awayTeamName = "testawayTeam_" + i + "_" + i;
            String matchIdentifier = "testMatch_" + i;

            String[] initialDateStr = getTimeByHoursFromNow(hoursToStart);
            LocalDateTime initialStartTime = DateUtil.getDate(initialDateStr[0], initialDateStr[1], initialDateStr[2], initialDateStr[3], initialDateStr[4]);

            expectedStartTimes.put(matchIdentifier, initialStartTime);
            newMatchesRaw.put("testMatch_" + i, (String[]) ArrayUtils.addAll(new String[]{homeTeamName,awayTeamName}, initialDateStr));

            if (i % 2 == 0) {
                Match match = new Match(matchIdentifier, new Team(awayTeamName), new Team(homeTeamName), initialStartTime);
                alreadyTrackedMatches.add(match);
            }

        }
        alreadyTrackedMatches.stream().forEach(m -> entityManager.persist(m));

        when(newMatchParser.parse(anyString())).thenReturn(newMatchesRaw);
        betExplorerCollector.findAndInsertNewMatches();

        List<Match> matches = entityManager.createQuery(" from Match m", Match.class).getResultList();
        List<Team> teams = entityManager.createQuery(" from Team t", Team.class).getResultList();
        assertEquals("Incorrect number of added matches!", expectedNoMatches, matches.size());
        assertEquals("Incorrect number of added teams!", expectedNoMatches * 2, teams.size());

        matches.forEach(m -> assertEquals("Different original and expected start time", expectedStartTimes.get(m.getIdentifier()), m.getStartTime()));


        rollbackTransaction();
    }



    @Test
    public void testFindAndInsertNewMatchesWithShift() {

        startTransaction();
        int expectedNoMatches = 8;
        Map<String, String[]> newMatchesRaw = Maps.newHashMap();
        List<Match> alreadyTrackedMatches = Lists.newArrayListWithCapacity(expectedNoMatches / 2);
        Map<String, LocalDateTime> expectedStartTimes = Maps.newHashMap();

        Random random = new Random();
        for (int i = 0; i < expectedNoMatches; i++) {
            int hoursToStart = (int) ((CommonConstants.DAYS_INTERVAL_BEFORE_MATCH_TO_ADD_LOWER_BOUNDARY + (0.5 *
                    (CommonConstants.DAYS_INTERVAL_BEFORE_MATCH_TO_ADD_UPPER_BOUNDARY - CommonConstants.DAYS_INTERVAL_BEFORE_MATCH_TO_ADD_LOWER_BOUNDARY))) * 24);

            String homeTeamName = "testhomeTeam_" + i + "_" + i;
            String awayTeamName = "testawayTeam_" + i + "_" + i;
            String matchIdentifier = "testMatch_" + i;

            String[] initialDateStr = getTimeByHoursFromNow(hoursToStart);
            LocalDateTime initialStartTime = DateUtil.getDate(initialDateStr[0], initialDateStr[1], initialDateStr[2], initialDateStr[3], initialDateStr[4]);


            if (i % 2 == 0) {
                int hoursToStartAfterShift = (int) ((CommonConstants.DAYS_INTERVAL_BEFORE_MATCH_TO_ADD_LOWER_BOUNDARY + (0.9 * random.nextDouble() *
                        (CommonConstants.DAYS_INTERVAL_BEFORE_MATCH_TO_ADD_UPPER_BOUNDARY - CommonConstants.DAYS_INTERVAL_BEFORE_MATCH_TO_ADD_LOWER_BOUNDARY))) * 24);
                String[] dateStrAfterShift = getTimeByHoursFromNow(hoursToStartAfterShift);
                LocalDateTime startTimeAfterShift = DateUtil.getDate(dateStrAfterShift[0], dateStrAfterShift[1], dateStrAfterShift[2], dateStrAfterShift[3], dateStrAfterShift[4]);

                Match match = new Match(matchIdentifier, new Team(homeTeamName), new Team(awayTeamName), initialStartTime);
                alreadyTrackedMatches.add(match);
                expectedStartTimes.put(matchIdentifier, startTimeAfterShift);
                newMatchesRaw.put("testMatch_" + i, (String[]) ArrayUtils.addAll(new String[]{homeTeamName, awayTeamName}, dateStrAfterShift));
            } else {
                expectedStartTimes.put(matchIdentifier, initialStartTime);
                newMatchesRaw.put("testMatch_" + i, (String[]) ArrayUtils.addAll(new String[]{homeTeamName, awayTeamName}, initialDateStr));
            }

        }
        alreadyTrackedMatches.stream().forEach(m -> entityManager.persist(m));

        when(newMatchParser.parse(anyString())).thenReturn(newMatchesRaw);
        betExplorerCollector.findAndInsertNewMatches();

        List<Match> matches = entityManager.createQuery(" from Match m", Match.class).getResultList();
        List<Team> teams = entityManager.createQuery(" from Team t", Team.class).getResultList();
        assertEquals("Incorrect number of added matches!", expectedNoMatches, matches.size());
        assertEquals("Incorrect number of added teams!", expectedNoMatches * 2, teams.size());

        matches.forEach(m -> assertEquals("Different original and expected start time", expectedStartTimes.get(m.getIdentifier()), m.getStartTime()));


        rollbackTransaction();
    }


    @Test
    public void testUpdateMatchOdds() {

        startTransaction();

        int noMatches = 100;
        int noBookmakers = 43;
        BigDecimal minOdd = new BigDecimal("1.01");
        BigDecimal maxOdd = new BigDecimal("25.00");
        List<Match> matches = Lists.newArrayList();
        List<Bookmaker> bookmakers = Lists.newArrayList();

        LocalDateTime startTime = LocalDateTime.now().plusDays(1);

        IntStream.range(0, noMatches).forEach(i -> matches
                .add(new Match("testMatch" + i, new Team("homeTeam_" + i), new Team("awayTeam_" + (2 * i + 1)), startTime)));

        IntStream.range(0, noBookmakers).forEach(i -> bookmakers.add(new Bookmaker("testUrl_" + i, "label_" + i)));
        Bookmaker avgBookmaker = new Bookmaker(BetExplorerCollector.AVERAGE_NAME, BetExplorerCollector.AVERAGE_NAME);
        bookmakers.add(avgBookmaker);

        matches.stream().forEach(match -> entityManager.persist(match));
        bookmakers.stream().forEach(bookmaker -> entityManager.persist(bookmaker));
        bookmakers.stream().forEach(b -> entityManager.persist(new BookmakerName(b, b.getLabel(), BetExplorerCollector.SYSTEM_NAME)));

        commitTransaction();
        entityManager.clear();

        Table<Match, Bookmaker, BigDecimal[]> odds1X2 = HashBasedTable.create();
        Table<Match, Bookmaker, BigDecimal[]> oddsDC = HashBasedTable.create();
        Table<Match, Bookmaker, Map<String, BigDecimal[]>> oddsOU = HashBasedTable.create();
        Table<Match, Bookmaker, BigDecimal[]> oddsBTS = HashBasedTable.create();

        for (Match match : matches) {
            bookmakers.stream().filter(bookmaker -> !bookmaker.equals(avgBookmaker)).forEach(bookmaker -> {

                odds1X2.put(match, bookmaker, new BigDecimal[]{
                        getRandom(minOdd, maxOdd, 2),
                        getRandom(minOdd, maxOdd, 2),
                        getRandom(minOdd, maxOdd, 2)});

                oddsDC.put(match, bookmaker, new BigDecimal[]{
                        getRandom(minOdd, maxOdd, 2),
                        getRandom(minOdd, maxOdd, 2),
                        getRandom(minOdd, maxOdd, 2)});

                oddsBTS.put(match, bookmaker, new BigDecimal[]{
                        getRandom(minOdd, maxOdd, 2),
                        getRandom(minOdd, maxOdd, 2)});


                oddsOU.put(match, bookmaker, Stream.of(MatchOdd.SupportedOU.values()).
                        collect(Collectors.toMap(MatchOdd.SupportedOU::toString, ou -> {
                            return new BigDecimal[]{getRandom(minOdd, maxOdd, 2), getRandom(minOdd, maxOdd, 2)};
                        })));
            });
        }

        for (Match match : matches) {
            BigDecimal avg1 = bookmakers.stream().filter(b -> !avgBookmaker.equals(b)).map(b -> odds1X2.get(match, b)[0]).reduce(BigDecimal::add).get().divide(new BigDecimal(bookmakers.size()), 2, RoundingMode.HALF_UP);
            BigDecimal avgX = bookmakers.stream().filter(b -> !avgBookmaker.equals(b)).map(b -> odds1X2.get(match, b)[1]).reduce(BigDecimal::add).get().divide(new BigDecimal(bookmakers.size()), 2, RoundingMode.HALF_UP);
            BigDecimal avg2 = bookmakers.stream().filter(b -> !avgBookmaker.equals(b)).map(b -> odds1X2.get(match, b)[2]).reduce(BigDecimal::add).get().divide(new BigDecimal(bookmakers.size()), 2, RoundingMode.HALF_UP);
            odds1X2.put(match, avgBookmaker, new BigDecimal[]{avg1, avgX, avg2});

            BigDecimal avg1X = bookmakers.stream().filter(b -> !avgBookmaker.equals(b)).map(b -> oddsDC.get(match, b)[0]).reduce(BigDecimal::add).get().divide(new BigDecimal(bookmakers.size()), 2, RoundingMode.HALF_UP);
            BigDecimal avg12 = bookmakers.stream().filter(b -> !avgBookmaker.equals(b)).map(b -> oddsDC.get(match, b)[1]).reduce(BigDecimal::add).get().divide(new BigDecimal(bookmakers.size()), 2, RoundingMode.HALF_UP);
            BigDecimal avgX2 = bookmakers.stream().filter(b -> !avgBookmaker.equals(b)).map(b -> oddsDC.get(match, b)[2]).reduce(BigDecimal::add).get().divide(new BigDecimal(bookmakers.size()), 2, RoundingMode.HALF_UP);
            oddsDC.put(match, avgBookmaker, new BigDecimal[]{avg1X, avg12, avgX2});

            BigDecimal avgBtsY = bookmakers.stream().filter(b -> !avgBookmaker.equals(b)).map(b -> oddsBTS.get(match, b)[0]).reduce(BigDecimal::add).get().divide(new BigDecimal(bookmakers.size()), 2, RoundingMode.HALF_UP);
            BigDecimal avgBtsN = bookmakers.stream().filter(b -> !avgBookmaker.equals(b)).map(b -> oddsBTS.get(match, b)[1]).reduce(BigDecimal::add).get().divide(new BigDecimal(bookmakers.size()), 2, RoundingMode.HALF_UP);
            oddsBTS.put(match, avgBookmaker, new BigDecimal[]{avgBtsY, avgBtsN});

            Map<String, BigDecimal[]> ouAvgs = Maps.newHashMap();
            Stream.of(MatchOdd.SupportedOU.values()).forEach(ou -> {
                BigDecimal over = bookmakers.stream().filter(b -> !avgBookmaker.equals(b)).map(b -> oddsOU.get(match, b).get(ou.toString())[0]).reduce(BigDecimal::add).get().divide(new BigDecimal(bookmakers.size()), 2, RoundingMode.HALF_UP);
                BigDecimal under = bookmakers.stream().filter(b -> !avgBookmaker.equals(b)).map(b -> oddsOU.get(match, b).get(ou.toString())[1]).reduce(BigDecimal::add).get().divide(new BigDecimal(bookmakers.size()), 2, RoundingMode.HALF_UP);
                ouAvgs.put(ou.toString(), new BigDecimal[]{over, under});
            });

            oddsOU.put(match, avgBookmaker, ouAvgs);

        }

        for (Match match : matches) {
            Map<String, String[]> raw1X2 = Maps.newHashMap();
            Map<String, String[]> rawDC = Maps.newHashMap();
            Map<String, String[]> rawBTS = Maps.newHashMap();
            Table<String, String, String[]> rawOU = HashBasedTable.create();

            for (Bookmaker bookmaker : bookmakers) {

                raw1X2.put(bookmaker.getLabel(), new String[]{
                        odds1X2.get(match, bookmaker)[0].toString(),
                        odds1X2.get(match, bookmaker)[1].toString(),
                        odds1X2.get(match, bookmaker)[2].toString()});
                rawDC.put(bookmaker.getLabel(), new String[]{
                        oddsDC.get(match, bookmaker)[0].toString(),
                        oddsDC.get(match, bookmaker)[1].toString(),
                        oddsDC.get(match, bookmaker)[2].toString()});
                rawBTS.put(bookmaker.getLabel(), new String[]{
                        oddsBTS.get(match, bookmaker)[0].toString(),
                        oddsBTS.get(match, bookmaker)[1].toString()});
                Stream.of(MatchOdd.SupportedOU.values()).forEach(ou -> rawOU.put(bookmaker.getLabel(), ou.toString(),
                        new String[]{oddsOU.get(match, bookmaker).get(ou.toString())[0].toString(),
                                oddsOU.get(match, bookmaker).get(ou.toString())[1].toString()}));
            }
            when(odds1X2Parser.parse(Matchers.contains(match.getIdentifier()))).thenReturn(raw1X2);
            when(oddsDCParser.parse(Matchers.contains(match.getIdentifier()))).thenReturn(rawDC);
            when(oddsBTSParser.parse(Matchers.contains(match.getIdentifier()))).thenReturn(rawBTS);
            when(oddsOUParser.parse(Matchers.contains(match.getIdentifier()))).thenReturn(rawOU);

        }
        startTransaction();
        mockWebBrowserToIdentity(webPageBrowser);


        betExplorerCollector.updateMatchOdds();


        List<MatchOdd> matchOdds = entityManager.createQuery(" from MatchOdd mo", MatchOdd.class)
                .getResultList();
        List<Match> freshMatches = entityManager.createQuery(" from Match m", Match.class).getResultList();

        assertEquals("Incorrect number of match odds!", noMatches * (noBookmakers + 1), matchOdds.size());

        Map<MatchOdd.Type, BigDecimal> computedAvgOdds = Maps.newEnumMap(MatchOdd.Type.class);
        Map<MatchOdd.Type, BigDecimal> originalAvgOdds = Maps.newEnumMap(MatchOdd.Type.class);

        for (MatchOdd.Type oddType : MatchOdd.Type.values()) {
            for (Match match : freshMatches) {
                computedAvgOdds.put(oddType, match.getMatchOdds().stream().filter(mo -> !avgBookmaker.equals(mo.getBookmaker())).
                        map(mo -> mo.getOdd(oddType)).reduce(BigDecimal::add).get().divide(new BigDecimal(bookmakers.size()), 2, RoundingMode.HALF_UP));
                originalAvgOdds.put(oddType,
                        match.getMatchOdds().stream().filter(mo -> avgBookmaker.equals(mo.getBookmaker())).map(mo -> mo.getOdd(oddType)).findFirst().get());
            }
        }

        for (MatchOdd.Type oddType : MatchOdd.Type.values()) {
            assertEquals("Type: " + oddType, originalAvgOdds.get(oddType), computedAvgOdds.get(oddType));
        }

        rollbackTransaction();
    }

    @Test
    public void testCheckMatchResultsNormalFinish() {

        startTransaction();
        int noMatches = 10;
        Map<String, String> browserResponses = Maps.newHashMap();
        List<Match> matches = Lists.newArrayList();
        Map<Match, String> resultMap = Maps.newHashMap();
        Map<String, MatchResult> resultMapEnum = Maps.newHashMap();
        List<String> results = Arrays.asList("0:0", "0:0", "1:0", "0:11", "2:3", "4:1", "5:1", "2:2", "11:12", "2:0");
        List<MatchResult> resultsEnum = results.stream().map(MatchResult::fromString).collect(Collectors.toList());

        LocalDateTime startTime = LocalDateTime.now().minusHours(3);
        IntStream.range(0, noMatches).forEach(i -> {
            Match match = new Match("testMatch_" + i, new Team("testTeam_" + i), new Team("testTeam_" + (noMatches + i + 1)),
                    startTime);
            matches.add(match);
            resultMap.put(match, results.get(i));
            resultMapEnum.put(match.getIdentifier(), resultsEnum.get(i));
        });

        matches.stream().forEach(match -> entityManager.persist(match));

        Map<String, List<Match>> matchResultsUrls = matches.stream().collect(Collectors.groupingBy(match -> {

            String[] dateStr = DateUtil.getDateInFormat(match.getStartTime().toLocalDate(), "dd.MM.yyyy").split("\\.");
            return BetExplorerCollector.PATTERN_MATCH_RESULT_URL.replace("#YYYY", dateStr[2]).replace("#MM", dateStr[1])
                    .replace("#DD", dateStr[0]);

        }));

        matchResultsUrls.forEach((url, matchesList) -> {
            StringBuilder sb = new StringBuilder();
            matchesList.forEach(
                    match -> sb.append(patternMatchResult.replace("#MATCH_ID", match.getIdentifier())
                            .replace("#RESULT", resultMap.get(match))));
            browserResponses.put(url, sb.toString());
        });

        mockWebBrowserToMapBased(webPageBrowser, browserResponses);
        betExplorerCollector.checkMatchResults();

        List<Match> freshMatches = entityManager.createQuery(" from Match m", Match.class).getResultList();

        assertEquals("Number of refreshed matches is different from expected!", noMatches, freshMatches.size());
        freshMatches.stream().forEach(match -> assertEquals("Expected and retrieved match results differ!",
                resultMapEnum.get(match.getIdentifier()), match.getResult()));

        rollbackTransaction();

    }
    

    @Test
    public void testCheckMatchResultsPenalties() {

        startTransaction();
        int noMatches = 4;
        Map<String, String> browserResponses = Maps.newHashMap();
        List<Match> matches = Lists.newArrayList();
        Map<Match, String> resultMap = Maps.newHashMap();
        Map<Match, String> partialResultMap = Maps.newHashMap();
        Map<Match, MatchResult> resultMapEnum = Maps.newHashMap();

        List<EnumMap<PartialMatchResult, Map.Entry<Integer, Integer>>> partialResultsRaw = Lists.newArrayList();
        partialResultsRaw.add(new EnumMap<>(Maps.newEnumMap(ImmutableMap.<PartialMatchResult, Entry<Integer, Integer>>builder().
                put(PartialMatchResult.HALF, Maps.immutableEntry(1, 0)).
                put(PartialMatchResult.FULL, Maps.immutableEntry(2, 3)).
                put(PartialMatchResult.ET_HALF, Maps.immutableEntry(0, 0)).
                put(PartialMatchResult.ET_FULL, Maps.immutableEntry(1, 0)).
                build()
        )));
        partialResultsRaw.add(new EnumMap<>(Maps.newEnumMap(ImmutableMap.<PartialMatchResult, Entry<Integer, Integer>>builder().
                put(PartialMatchResult.HALF, Maps.immutableEntry(0, 0)).
                put(PartialMatchResult.FULL, Maps.immutableEntry(1, 2)).
                put(PartialMatchResult.ET_HALF, Maps.immutableEntry(1, 0)).
                put(PartialMatchResult.ET_FULL, Maps.immutableEntry(1, 0)).
                build()
        )));
        partialResultsRaw.add(new EnumMap<>(Maps.newEnumMap(ImmutableMap.<PartialMatchResult, Entry<Integer, Integer>>builder().
                put(PartialMatchResult.HALF, Maps.immutableEntry(1, 0)).
                put(PartialMatchResult.FULL, Maps.immutableEntry(1, 0)).
                put(PartialMatchResult.ET_HALF, Maps.immutableEntry(0, 0)).
                put(PartialMatchResult.ET_FULL, Maps.immutableEntry(1, 0)).
                build()
        )));
        partialResultsRaw.add(new EnumMap<>(Maps.newEnumMap(ImmutableMap.<PartialMatchResult, Entry<Integer, Integer>>builder().
                put(PartialMatchResult.HALF, Maps.immutableEntry(1, 0)).
                put(PartialMatchResult.FULL, Maps.immutableEntry(0, 1)).
                put(PartialMatchResult.ET_HALF, Maps.immutableEntry(2, 1)).
                put(PartialMatchResult.ET_FULL, Maps.immutableEntry(0, 1)).
                build()
        )));


        List<String> results = partialResultsRaw.stream().map(partRes -> {
            int homeScore = partRes.get(PartialMatchResult.HALF).getKey() + partRes.get(PartialMatchResult.FULL).getKey();
            int awayScore = partRes.get(PartialMatchResult.HALF).getValue() + partRes.get(PartialMatchResult.FULL).getValue();
            return homeScore + ":" + awayScore;
        }).collect(Collectors.toList());

        List<String> partialResults = partialResultsRaw.stream().map(partRes -> partRes.get(PartialMatchResult.HALF).getKey() + ":" + partRes.get(PartialMatchResult.HALF).getValue() + ", " +
                partRes.get(PartialMatchResult.FULL).getKey() + ":" + partRes.get(PartialMatchResult.FULL).getValue() + ", " +
                partRes.get(PartialMatchResult.ET_HALF).getKey() + ":" + partRes.get(PartialMatchResult.ET_FULL).getValue() + ", " +
                partRes.get(PartialMatchResult.ET_FULL).getKey() + ":" + partRes.get(PartialMatchResult.ET_FULL).getValue()).collect(Collectors.toList());

        List<MatchResult> resultsEnum = results.stream().map(MatchResult::fromString).collect(Collectors.toList());

        LocalDateTime startTime = LocalDateTime.now().minusHours(3);
        IntStream.range(0, noMatches).forEach(i -> {
            Match match = new Match("testMatch_" + i, new Team("testTeam_" + i), new Team("testTeam_" + (noMatches + i + 1)),
                    startTime);
            matches.add(match);
            resultMap.put(match, results.get(i));
            partialResultMap.put(match, partialResults.get(i));
            resultMapEnum.put(match, resultsEnum.get(i));
        });

        matches.stream().forEach(match -> entityManager.persist(match));

        Map<String, List<Match>> matchResultsUrls = matches.stream().collect(Collectors.groupingBy(match -> {

            String[] dateStr = DateUtil.getDateInFormat(match.getStartTime().toLocalDate(), "dd.MM.yyyy").split("\\.");
            return BetExplorerCollector.PATTERN_MATCH_RESULT_URL.replace("#YYYY", dateStr[2]).replace("#MM", dateStr[1])
                    .replace("#DD", dateStr[0]);

        }));

        matchResultsUrls.forEach((url, matchesList) -> {
            StringBuilder sb = new StringBuilder();
            matchesList.forEach(
                    match -> sb.append(patternMatchResultPenalties.replace("#MATCH_ID", match.getIdentifier())
                            .replace("#RESULT", resultMap.get(match)).replace("#PARTIAL_RESULTS", partialResultMap.get(match))));
            browserResponses.put(url, sb.toString());
        });

        mockWebBrowserToMapBased(webPageBrowser, browserResponses);
        betExplorerCollector.checkMatchResults();

        List<Match> freshMatches = entityManager.createQuery(" from Match m", Match.class).getResultList();

        assertEquals("Number of refreshed matches is different from expected!", noMatches, freshMatches.size());
        freshMatches.stream().forEach(match -> assertEquals("Expected and retrieved match results differ!",
                resultMapEnum.get(match), match.getResult()));

        rollbackTransaction();

    }

    @Test
    public void testCheckMatchResultsExtraTime() {

        startTransaction();
        int noMatches = 4;
        Map<String, String> browserResponses = Maps.newHashMap();
        List<Match> matches = Lists.newArrayList();
        Map<Match, String> resultMap = Maps.newHashMap();
        Map<Match, String> partialResultMap = Maps.newHashMap();
        Map<Match, MatchResult> resultMapEnum = Maps.newHashMap();

        List<EnumMap<PartialMatchResult, Map.Entry<Integer, Integer>>> partialResultsRaw = Lists.newArrayList();
        partialResultsRaw.add(new EnumMap<>(Maps.newEnumMap(ImmutableMap.<PartialMatchResult, Entry<Integer, Integer>>builder().
                put(PartialMatchResult.HALF, Maps.immutableEntry(1, 0)).
                put(PartialMatchResult.FULL, Maps.immutableEntry(2, 3)).
                put(PartialMatchResult.ET_HALF, Maps.immutableEntry(2, 0)).
                build()
        )));
        partialResultsRaw.add(new EnumMap<>(Maps.newEnumMap(ImmutableMap.<PartialMatchResult, Entry<Integer, Integer>>builder().
                put(PartialMatchResult.HALF, Maps.immutableEntry(0, 1)).
                put(PartialMatchResult.FULL, Maps.immutableEntry(1, 2)).
                put(PartialMatchResult.ET_HALF, Maps.immutableEntry(4, 0)).
                put(PartialMatchResult.ET_FULL, Maps.immutableEntry(1, 0)).
                build()
        )));
        partialResultsRaw.add(new EnumMap<>(Maps.newEnumMap(ImmutableMap.<PartialMatchResult, Entry<Integer, Integer>>builder().
                put(PartialMatchResult.HALF, Maps.immutableEntry(1, 0)).
                put(PartialMatchResult.FULL, Maps.immutableEntry(1, 0)).
                put(PartialMatchResult.ET_HALF, Maps.immutableEntry(0, 0)).
                build()
        )));
        partialResultsRaw.add(new EnumMap<>(Maps.newEnumMap(ImmutableMap.<PartialMatchResult, Entry<Integer, Integer>>builder().
                put(PartialMatchResult.HALF, Maps.immutableEntry(0, 0)).
                put(PartialMatchResult.FULL, Maps.immutableEntry(1, 1)).
                put(PartialMatchResult.ET_HALF, Maps.immutableEntry(0, 0)).
                put(PartialMatchResult.ET_FULL, Maps.immutableEntry(1, 1)).
                build()
        )));


        List<String> results = partialResultsRaw.stream().map(partRes -> {
            int homeScore = partRes.get(PartialMatchResult.HALF).getKey() + partRes.get(PartialMatchResult.FULL).getKey();
            int awayScore = partRes.get(PartialMatchResult.HALF).getValue() + partRes.get(PartialMatchResult.FULL).getValue();
            return homeScore + ":" + awayScore;
        }).collect(Collectors.toList());

        List<String> partialResults = partialResultsRaw.stream().map(partRes -> {
            StringBuilder sb = new StringBuilder();
            sb.append(partRes.get(PartialMatchResult.HALF).getKey()).append(":").append(partRes.get(PartialMatchResult.HALF).getValue()).append(", ").
                    append(partRes.get(PartialMatchResult.FULL).getKey()).append(":").append(partRes.get(PartialMatchResult.FULL).getValue());
            if (partRes.get(PartialMatchResult.ET_HALF) != null) {
                sb.append(", ").append(partRes.get(PartialMatchResult.ET_HALF).getKey()).append(":").append(partRes.get(PartialMatchResult.ET_HALF).getValue());
            }
            if (partRes.get(PartialMatchResult.ET_FULL) != null) {
                sb.append(", ").append(partRes.get(PartialMatchResult.ET_FULL).getKey()).append(":").append(partRes.get(PartialMatchResult.ET_FULL).getValue());
            }
            return sb.toString();
        }).collect(Collectors.toList());


        List<MatchResult> resultsEnum = results.stream().map(MatchResult::fromString).collect(Collectors.toList());

        LocalDateTime startTime = LocalDateTime.now().minusHours(3);
        IntStream.range(0, noMatches).forEach(i -> {
            Match match = new Match("testMatch_" + i, new Team("testTeam_" + i), new Team("testTeam_" + (noMatches + i + 1)),
                    startTime);
            matches.add(match);
            resultMap.put(match, results.get(i));
            partialResultMap.put(match, partialResults.get(i));
            resultMapEnum.put(match, resultsEnum.get(i));
        });

        matches.stream().forEach(match -> entityManager.persist(match));

        Map<String, List<Match>> matchResultsUrls = matches.stream().collect(Collectors.groupingBy(match -> {

            String[] dateStr = DateUtil.getDateInFormat(match.getStartTime().toLocalDate(), "dd.MM.yyyy").split("\\.");
            return BetExplorerCollector.PATTERN_MATCH_RESULT_URL.replace("#YYYY", dateStr[2]).replace("#MM", dateStr[1])
                    .replace("#DD", dateStr[0]);

        }));

        matchResultsUrls.forEach((url, matchesList) -> {
            StringBuilder sb = new StringBuilder();
            matchesList.forEach(
                    match -> sb.append(patternMatchResultExtraTime.replace("#MATCH_ID", match.getIdentifier())
                            .replace("#RESULT", resultMap.get(match)).replace("#PARTIAL_RESULTS", partialResultMap.get(match))));
            browserResponses.put(url, sb.toString());
        });

        mockWebBrowserToMapBased(webPageBrowser, browserResponses);
        betExplorerCollector.checkMatchResults();

        List<Match> freshMatches = entityManager.createQuery(" from Match m", Match.class).getResultList();

        assertEquals("Number of refreshed matches is different from expected!", noMatches, freshMatches.size());
        freshMatches.stream().forEach(match -> assertEquals("Expected and retrieved match results differ!",
                resultMapEnum.get(match), match.getResult()));

        rollbackTransaction();

    }

    @Test
    public void testCheckMatchResultsPostponed() {

        startTransaction();
        int noMatches = 2;
        Map<String, String> browserResponses = Maps.newHashMap();
        List<Match> matches = Lists.newArrayList();
        Map<Match, MatchResult.Type> resultMapEnum = Maps.newHashMap();

        List<MatchResult.Type> resultsEnum = Lists.newArrayList(MatchResult.Type.POSTPONED, MatchResult.Type.POSTPONED);
        LocalDateTime startTime = LocalDateTime.now().minusHours(3 * (BetExplorerCollector.SECONDS_INTERVAL_TO_CHECK_MATCH_RESULT / 3600));

        IntStream.range(0, noMatches).forEach(i -> {
            Match match = new Match("testMatch_" + i, new Team("testTeam_" + i), new Team("testTeam_" + (noMatches + i + 1)),
                    startTime);
            matches.add(match);
            resultMapEnum.put(match, resultsEnum.get(i));
        });

        matches.stream().forEach(match -> entityManager.persist(match));

        Map<String, List<Match>> matchResultsUrls = matches.stream().collect(Collectors.groupingBy(match -> {

            String[] dateStr = DateUtil.getDateInFormat(match.getStartTime().toLocalDate(), "dd.MM.yyyy").split("\\.");
            return BetExplorerCollector.PATTERN_MATCH_RESULT_URL.replace("#YYYY", dateStr[2]).replace("#MM", dateStr[1])
                    .replace("#DD", dateStr[0]);

        }));

        matchResultsUrls.forEach((url, matchesList) -> {
            StringBuilder sb = new StringBuilder();
            matchesList.forEach(
                    match -> sb.append(patternMatchResultPostponed.replace("#MATCH_ID", match.getIdentifier())
                    ));
            browserResponses.put(url, sb.toString());
        });
        mockWebBrowserToMapBased(webPageBrowser, browserResponses);

        betExplorerCollector.checkMatchResults();

        List<Match> freshMatches = entityManager.createQuery(" from Match m", Match.class).getResultList();

        assertEquals("Number of refreshed matches is different from expected!", noMatches, freshMatches.size());
        freshMatches.stream().forEach(match -> assertEquals("Expected and retrieved match results differ!",
                resultMapEnum.get(match), match.getResult().asType()));

        rollbackTransaction();

    }

    @Test
    public void testCheckMatchResultsCancelled() {

        startTransaction();
        int noMatches = 2;
        Map<String, String> browserResponses = Maps.newHashMap();
        List<Match> matches = Lists.newArrayList();
        Map<Match, MatchResult.Type> resultMapEnum = Maps.newHashMap();
        List<MatchResult.Type> resultsEnum = Lists.newArrayList(MatchResult.Type.CANCELLED, MatchResult.Type.CANCELLED);

        LocalDateTime startTime = LocalDateTime.now().minusHours(3);
        IntStream.range(0, noMatches).forEach(i -> {
            Match match = new Match("testMatch_" + i, new Team("testTeam_" + i), new Team("testTeam_" + (noMatches + i + 1)),
                    startTime);
            matches.add(match);
            resultMapEnum.put(match, resultsEnum.get(i));
        });

        matches.stream().forEach(match -> entityManager.persist(match));

        Map<String, List<Match>> matchResultsUrls = matches.stream().collect(Collectors.groupingBy(match -> {

            String[] dateStr = DateUtil.getDateInFormat(match.getStartTime().toLocalDate(), "dd.MM.yyyy").split("\\.");
            return BetExplorerCollector.PATTERN_MATCH_RESULT_URL.replace("#YYYY", dateStr[2]).replace("#MM", dateStr[1])
                    .replace("#DD", dateStr[0]);

        }));

        matchResultsUrls.forEach((url, matchesList) -> {
            StringBuilder sb = new StringBuilder();
            matchesList.forEach(
                    match -> sb.append(patternMatchResultCancelled.replace("#MATCH_ID", match.getIdentifier())
                    ));
            browserResponses.put(url, sb.toString());
        });

        mockWebBrowserToMapBased(webPageBrowser, browserResponses);

        betExplorerCollector.checkMatchResults();

        List<Match> freshMatches = entityManager.createQuery(" from Match m", Match.class).getResultList();

        assertEquals("Number of refreshed matches is different from expected!", noMatches, freshMatches.size());
        freshMatches.stream().forEach(match -> assertEquals("Expected and retrieved match results differ!",
                resultMapEnum.get(match), match.getResult().asType()));

        rollbackTransaction();

    }

    @Test
    public void testCheckMatchResultsNotFound() {

        startTransaction();
        int noMatches = 2;
        Map<String, String> browserResponses = Maps.newHashMap();
        List<Match> matches = Lists.newArrayList();
        Map<Match, MatchResult.Type> resultMapEnum = Maps.newHashMap();
        List<MatchResult.Type> resultsEnum = Lists.newArrayList(MatchResult.Type.NOT_FOUND, MatchResult.Type.NOT_FOUND);

        IntStream.range(0, noMatches).forEach(i -> {

            LocalDateTime startTime = LocalDateTime.now().minusSeconds((i + 1) * (BetExplorerCollector.SECONDS_INTERVAL_TO_INVALIDATE_MATCH + 1));
            Match match = new Match("testMatch_" + i, new Team("testTeam_" + i), new Team("testTeam_" + (noMatches + i + 1)),
                    startTime);
            matches.add(match);
            resultMapEnum.put(match, resultsEnum.get(i));
        });

        matches.stream().forEach(match -> entityManager.persist(match));

        Map<String, List<Match>> matchResultsUrls = matches.stream().collect(Collectors.groupingBy(match -> {

            String[] dateStr = DateUtil.getDateInFormat(match.getStartTime().toLocalDate(), "dd.MM.yyyy").split("\\.");
            return BetExplorerCollector.PATTERN_MATCH_RESULT_URL.replace("#YYYY", dateStr[2]).replace("#MM", dateStr[1])
                    .replace("#DD", dateStr[0]);

        }));

        matchResultsUrls.forEach((url, matchesList) -> browserResponses.put(url, " "));
        mockWebBrowserToMapBased(webPageBrowser, browserResponses);

        betExplorerCollector.checkMatchResults();

        List<Match> freshMatches = entityManager.createQuery(" from Match m", Match.class).getResultList();

        assertEquals("Number of refreshed matches is different from expected!", noMatches, freshMatches.size());
        freshMatches.stream().forEach(match -> assertEquals("Expected and retrieved match results differ!",
                resultMapEnum.get(match), match.getResult().asType()));

        rollbackTransaction();

    }

    private String[] getTimeByHoursFromNow(double hours) {
        String[] result = new String[5];

        LocalDateTime resultDate = LocalDateTime.now().plusMinutes((int) (hours * 60));
        String[] date = DateUtil.getDateInFormat(resultDate.toLocalDate(), "dd.MM.yyyy").split("\\.");

        result[0] = date[0];
        result[1] = date[1];
        result[2] = date[2];

        int resultHour = resultDate.getHour();
        int resultMinute = resultDate.getMinute();

        String minuteStr;
        if (resultMinute < 10) {
            minuteStr = "0" + resultMinute;
        } else {
            minuteStr = "" + resultMinute;
        }

        String hourStr;
        if (resultHour < 10) {
            hourStr = "0" + resultHour;
        } else {
            hourStr = resultHour + "";
        }

        result[3] = hourStr;
        result[4] = minuteStr;

        return result;

    }

    private enum PartialMatchResult {
        HALF, FULL, ET_HALF, ET_FULL

    }

}
