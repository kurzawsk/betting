package com.kk.betting.services.matchdatacollection.sourcesystems.betexplorer.dao;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.kk.betting.domain.*;
import com.kk.betting.services.common.dao.BookmakerDao;
import com.kk.betting.services.common.dao.MatchDao;
import com.kk.betting.services.common.dao.MatchOddDao;
import com.kk.betting.services.common.dao.TeamDao;
import com.kk.betting.services.common.service.WebPageBrowser;
import com.kk.betting.services.common.util.CommonConstants;
import com.kk.betting.services.common.util.DateUtil;
import com.kk.betting.services.matchdatacollection.service.AbstractMatchDataCollector;
import com.kk.betting.services.matchdatacollection.sourcesystems.betexplorer.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Named
public class BetExplorerCollector extends AbstractMatchDataCollector {

    private static final Log log = LogFactory.getLog(BetExplorerCollector.class);

    public static final String SYSTEM_NAME = "www.betexplorer.com";
    public static final String PINNACLE_SPORTS_NAME = "25";
    public static final String BET_AT_HOME_NAME = "102";
    public static final String AVERAGE_NAME = "Average";

    static final String MATCH_IDENTIFIER_SYMBOL = "$matchIdentifier";
    static final int SECONDS_INTERVAL_TO_CHECK_MATCH_RESULT = 7200;
    static final int SECONDS_INTERVAL_TO_INVALIDATE_MATCH = (int) (CommonConstants.DAYS_INTERVAL_TO_INVALIDATE_MATCH
            * DateUtil.secondsInDay);
    static final int SECONDS_INTERVAL_BEFORE_MATCH_TO_ADD_LOWER_BOUNDARY = (int) (CommonConstants.DAYS_INTERVAL_BEFORE_MATCH_TO_ADD_LOWER_BOUNDARY
            * DateUtil.secondsInDay);
    static final int SECONDS_INTERVAL_BEFORE_MATCH_TO_ADD_UPPER_BOUNDARY = (int) (CommonConstants.DAYS_INTERVAL_BEFORE_MATCH_TO_ADD_UPPER_BOUNDARY
            * DateUtil.secondsInDay);

    static final int ALLOWED_MATCH_SHIFT_INTERVAL_HOURS = 96;

    static final int CHECK_MATCH_RESULT_DAYS_OFFEST = CommonConstants.DAYS_INTERVAL_TO_INVALIDATE_MATCH;
    static final String BASE_ODDS_URL_TEMPLATE = "http://www.betexplorer.com/gres/ajax/matchodds.php?e=";
    static final String URL_1X2_TEMPLATE = BASE_ODDS_URL_TEMPLATE + MATCH_IDENTIFIER_SYMBOL + "&b=1x2";
    static final String URL_OU_TEMPLATE = BASE_ODDS_URL_TEMPLATE + MATCH_IDENTIFIER_SYMBOL + "&b=ou";
    static final String URL_1X2_DC_TEMPLATE = BASE_ODDS_URL_TEMPLATE + MATCH_IDENTIFIER_SYMBOL + "&b=dc";
    static final String URL_BTS_TEMPLATE = BASE_ODDS_URL_TEMPLATE + MATCH_IDENTIFIER_SYMBOL + "&b=bts";

    static final String MAIN_MATCHES_PAGE_LINK = "http://www.betexplorer.com/odds-filter/soccer/?rangeFrom=1&rangeTo=999&days=14";
    static final String PATTERN_MATCH_RESULT_URL = "http://www.betexplorer.com/next/soccer/?year=#YYYY&month=#MM&day=#DD";
    static final String REFERER = "Referer";

    @Inject
    private MatchDao matchDao;

    @Inject
    private TeamDao teamDao;

    @Inject
    private MatchOddDao matchOddDao;

    @Inject
    private BookmakerDao bookmakerDao;

    private Odds1X2Parser odds1X2Parser = new Odds1X2Parser();
    private OddsDCParser oddsDCParser = new OddsDCParser();
    private OddsOUParser oddsOUParser = new OddsOUParser();
    private OddsBTSParser oddsBTSParser = new OddsBTSParser();
    private MatchResultParser matchResultParser = new MatchResultParser();
    private NewMatchParser newMatchParser = new NewMatchParser();

    private Consumer<String[]> blankReplacer = arr -> {
        for (int i = 0; i < arr.length; i++) {
            if (StringUtils.isBlank(arr[i])) {
                arr[i] = null;
            }
        }
    };

    private Map<String, Bookmaker> bookmakersByName = Maps.newConcurrentMap();

    public void checkMatchResults() {
        checkMatchResults(LocalDateTime.now());
    }

    public void checkMatchResults(LocalDateTime referenceDate) {
        List<? extends Match> pendingMatches = matchDao.getUnfinishedMatchesByStartTime();
        List<Match> matchesToUpdate = Lists.newArrayList();
        List<Match> matchToCheckResultFor = Lists.newArrayList();

        for (Match match : pendingMatches) {
            long difference = ChronoUnit.SECONDS.between(match.getStartTime(), referenceDate);
            if (difference > SECONDS_INTERVAL_TO_CHECK_MATCH_RESULT && difference <= SECONDS_INTERVAL_TO_INVALIDATE_MATCH) {
                matchToCheckResultFor.add(match);
            } else if (difference > SECONDS_INTERVAL_TO_INVALIDATE_MATCH) {
                log.warn("Match " + match.getIdentifier() + " result has not been found " + difference
                        + " seconds after its start. Setting the result to: " + MatchResult.Type.NOT_FOUND);
                match.setResult(MatchResult.fromType(MatchResult.Type.NOT_FOUND));
                match.setIdentifier(match.getIdentifier() + "_I_" + System.currentTimeMillis());
                matchesToUpdate.add(match);
            }
        }

        log.info("There are " + matchToCheckResultFor.size() + " matches to check result of");

        if (!matchToCheckResultFor.isEmpty()) {
            Map<String, Match> matchesByIdentifier = matchToCheckResultFor.stream().
                    collect(Collectors.toMap(Match::getIdentifier, Function.identity()));
            Map<String, String[]> rawResults = getMatchResultsRaw(referenceDate);
            for (Entry<String, Match> entry : matchesByIdentifier.entrySet()) {

                String[] rawResult = rawResults.get(entry.getKey());
                if (rawResult != null) {
                    if (rawResult.length == 2) {
                        entry.getValue().setResult(MatchResult.fromScores(rawResult[0], rawResult[1]));
                        matchesToUpdate.add(entry.getValue());
                    } else if (rawResult.length == 1) {
                        for (MatchResult.Type formallyAnulled : MatchResult.Type.FORMALLY_ANNULLED) {
                            if (formallyAnulled.toString().equals(rawResult[0])) {
                                entry.getValue().setResult(MatchResult.fromType(formallyAnulled));
                                entry.getValue().setIdentifier(entry.getValue().getIdentifier() + "_I_" + System.currentTimeMillis());
                                matchesToUpdate.add(entry.getValue());
                                break;
                            }
                        }
                    }
                }
            }
        }

        matchDao.persist(matchesToUpdate);
        distributeMatchFinished(matchesToUpdate);
    }

    public void updateMatchOdds() {
        reinitializeBookmakers();
        List<MatchOdd> oddsToAdd = Lists.newArrayList();
        List<? extends Match> matches = matchDao.getUnfinishedMatchesByStartTime();

        Long size = Math.min(WebPageBrowser.DEFAULT_MAX_CONNECTIONS, Math.round(Math.sqrt(matches.size() / 2)));
        log.info("There will be " + matches.size() + " matches to be searched for MatchOdds in threads: " + size);

        if (size <= 0) {
            log.warn("No matches to update odds for");
            return;
        }

        BlockingQueue<Match> matchesToProcess = new LinkedBlockingQueue<>(matches);
        ExecutorService executorService = Executors.newFixedThreadPool(size.intValue());

        Callable<List<MatchOdd>> oddsExtractor = () -> {
            List<MatchOdd> odds = Lists.newLinkedList();
            Match match = matchesToProcess.poll();
            while (Objects.nonNull(match)) {
                odds.addAll(getMatchOddsFromWebPage(match));
                match = matchesToProcess.poll();
            }
            return odds;
        };

        List<Future<List<MatchOdd>>> futureResults;
        try {
            futureResults = executorService.invokeAll(LongStream.range(0, size).mapToObj(i -> oddsExtractor).collect(Collectors.toList()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        oddsToAdd.addAll(futureResults.stream().map(f -> {
            try {
                return f.get();
            } catch (InterruptedException | ExecutionException e) {
                log.warn("Interrupting after " + matchesToProcess.size() + " of " + matches.size() + " matches processed for odds");
                throw new RuntimeException(e);
            }
        }).flatMap(List::stream).collect(Collectors.toList()));
        matchOddDao.persist(oddsToAdd);
    }


    public void findAndInsertNewMatches() {
        List<Match> matchesFromDataBase = (List<Match>) matchDao.getUnfinishedMatchesByStartTime();
        List<Match> matchesFromWebPage = getNewMatchesFromWebPage();
        List<Match> matchesToAdd = filterAlreadyExistingMatches(matchesFromWebPage, matchesFromDataBase, ALLOWED_MATCH_SHIFT_INTERVAL_HOURS);

        log.info("There are " + matchesToAdd.size() + " matches to add");
        matchDao.persist(matchesToAdd);
    }

    private void reinitializeBookmakers() {
        bookmakersByName.clear();
        bookmakersByName.putAll(
                bookmakerDao.findAll().stream().collect(Collectors.toMap(b -> b.getName(SYSTEM_NAME), Function.identity())));
    }

    private Map<String, String[]> getCurrent1X2MatchOddsRaw(String matchIdentifier) {
        String url = URL_1X2_TEMPLATE.replace(MATCH_IDENTIFIER_SYMBOL, matchIdentifier);
        return odds1X2Parser.parse(browser.doGet(url, ImmutableMap.of(REFERER, url)));
    }

    private Map<String, String[]> getCurrent1X2DCMatchOddsRaw(String matchIdentifier) {
        String url = URL_1X2_DC_TEMPLATE.replace(MATCH_IDENTIFIER_SYMBOL, matchIdentifier);
        return oddsDCParser.parse(browser.doGet(url, ImmutableMap.of(REFERER, url)));
    }

    private Map<String, String[]> getCurrentBTSMatchOddsRaw(String matchIdentifier) {
        String url = URL_BTS_TEMPLATE.replace(MATCH_IDENTIFIER_SYMBOL, matchIdentifier);
        return oddsBTSParser.parse(browser.doGet(url, ImmutableMap.of(REFERER, url)));
    }

    private Map<String, String[]> getMatchResultsRaw(LocalDateTime referenceDate) {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i <= CHECK_MATCH_RESULT_DAYS_OFFEST; i++) {
            String[] dayArr = DateUtil.getDateInFormat(referenceDate.toLocalDate().minusDays(i), "dd.MM.yyyy").split("\\.");
            String url = PATTERN_MATCH_RESULT_URL.replace("#YYYY", dayArr[2]).replace("#MM", dayArr[1]).replace("#DD", dayArr[0]);
            content.append(browser.doGet(url));
        }

        return matchResultParser.parse(content.toString());
    }

    private Table<String, String, String[]> getCurrentOUMatchOddsRaw(String matchIdentifier) {
        Map<String, String[]> result = Maps.newHashMap();
        String url = URL_OU_TEMPLATE.replace(MATCH_IDENTIFIER_SYMBOL, matchIdentifier);
        return oddsOUParser.parse(browser.doGet(url, ImmutableMap.of(REFERER, url)));
    }

    private List<MatchOdd> getMatchOddsFromWebPage(Match match) {
        List<MatchOdd> result = Lists.newArrayList();
        Map<String, String[]> odds1X2Raw = getCurrent1X2MatchOddsRaw(match.getIdentifier());
        Map<String, String[]> odds1X2DCRaw = getCurrent1X2DCMatchOddsRaw(match.getIdentifier());
        Map<String, String[]> oddsBTSRaw = getCurrentBTSMatchOddsRaw(match.getIdentifier());
        Table<String, String, String[]> oddsOURaw = getCurrentOUMatchOddsRaw(match.getIdentifier());


        Set<String> relevantBookmakerNames = odds1X2Raw.keySet().stream()
                .filter(bookmakerName -> bookmakersByName.containsKey(bookmakerName)).collect(Collectors.toSet());

        odds1X2Raw.values().stream().forEach(blankReplacer);
        odds1X2DCRaw.values().stream().forEach(blankReplacer);
        oddsBTSRaw.values().stream().forEach(blankReplacer);
        oddsOURaw.values().stream().forEach(blankReplacer);

        relevantBookmakerNames.stream().forEach(relevantBookmakerName -> {

            MatchOdd.MatchOddBuilder matchOddBuilder = new MatchOdd.MatchOddBuilder();

            matchOddBuilder.setMatch(match).setTimestamp(LocalDateTime.now()).setBookmaker(bookmakersByName.get(relevantBookmakerName))
                    .setOdd1(NumberUtils.createBigDecimal(odds1X2Raw.get(relevantBookmakerName)[0]))
                    .setOddX(NumberUtils.createBigDecimal(odds1X2Raw.get(relevantBookmakerName)[1]))
                    .setOdd2(NumberUtils.createBigDecimal(odds1X2Raw.get(relevantBookmakerName)[2]));

            if (Objects.nonNull(odds1X2DCRaw.get(relevantBookmakerName))) {
                matchOddBuilder
                        .setOdd1X(NumberUtils.createBigDecimal(odds1X2DCRaw.get(relevantBookmakerName)[0]))
                        .setOdd12(NumberUtils.createBigDecimal(odds1X2DCRaw.get(relevantBookmakerName)[1]))
                        .setOddX2(NumberUtils.createBigDecimal(odds1X2DCRaw.get(relevantBookmakerName)[2]));
            }

            if (Objects.nonNull(oddsBTSRaw.get(relevantBookmakerName))) {
                matchOddBuilder
                        .setOddBTSY(NumberUtils.createBigDecimal(oddsBTSRaw.get(relevantBookmakerName)[0]))
                        .setOddBTSN(NumberUtils.createBigDecimal(oddsBTSRaw.get(relevantBookmakerName)[1]));
            }

            if (oddsOURaw.contains(relevantBookmakerName, MatchOdd.SupportedOU._05.toString())) {
                matchOddBuilder
                        .setOddO05(NumberUtils.createBigDecimal(oddsOURaw.get(relevantBookmakerName, MatchOdd.SupportedOU._05.toString())[0]))
                        .setOddU05(NumberUtils.createBigDecimal(oddsOURaw.get(relevantBookmakerName, MatchOdd.SupportedOU._05.toString())[1]));
            }
            if (oddsOURaw.contains(relevantBookmakerName, MatchOdd.SupportedOU._15.toString())) {
                matchOddBuilder
                        .setOddO15(NumberUtils.createBigDecimal(oddsOURaw.get(relevantBookmakerName, MatchOdd.SupportedOU._15.toString())[0]))
                        .setOddU15(NumberUtils.createBigDecimal(oddsOURaw.get(relevantBookmakerName, MatchOdd.SupportedOU._15.toString())[1]));
            }
            if (oddsOURaw.contains(relevantBookmakerName, MatchOdd.SupportedOU._25.toString())) {
                matchOddBuilder
                        .setOddO25(NumberUtils.createBigDecimal(oddsOURaw.get(relevantBookmakerName, MatchOdd.SupportedOU._25.toString())[0]))
                        .setOddU25(NumberUtils.createBigDecimal(oddsOURaw.get(relevantBookmakerName, MatchOdd.SupportedOU._25.toString())[1]));
            }

            if (oddsOURaw.contains(relevantBookmakerName, MatchOdd.SupportedOU._35.toString())) {
                matchOddBuilder
                        .setOddO35(NumberUtils.createBigDecimal(oddsOURaw.get(relevantBookmakerName, MatchOdd.SupportedOU._35.toString())[0]))
                        .setOddU35(NumberUtils.createBigDecimal(oddsOURaw.get(relevantBookmakerName, MatchOdd.SupportedOU._35.toString())[1]));
            }

            if (oddsOURaw.contains(relevantBookmakerName, MatchOdd.SupportedOU._45.toString())) {
                matchOddBuilder
                        .setOddO45(NumberUtils.createBigDecimal(oddsOURaw.get(relevantBookmakerName, MatchOdd.SupportedOU._45.toString())[0]))
                        .setOddU45(NumberUtils.createBigDecimal(oddsOURaw.get(relevantBookmakerName, MatchOdd.SupportedOU._45.toString())[1]));
            }

            if (oddsOURaw.contains(relevantBookmakerName, MatchOdd.SupportedOU._55.toString())) {
                matchOddBuilder
                        .setOddO55(NumberUtils.createBigDecimal(oddsOURaw.get(relevantBookmakerName, MatchOdd.SupportedOU._55.toString())[0]))
                        .setOddU55(NumberUtils.createBigDecimal(oddsOURaw.get(relevantBookmakerName, MatchOdd.SupportedOU._55.toString())[1]));
            }
            if (oddsOURaw.contains(relevantBookmakerName, MatchOdd.SupportedOU._65.toString())) {
                matchOddBuilder
                        .setOddO65(NumberUtils.createBigDecimal(oddsOURaw.get(relevantBookmakerName, MatchOdd.SupportedOU._65.toString())[0]))
                        .setOddU65(NumberUtils.createBigDecimal(oddsOURaw.get(relevantBookmakerName, MatchOdd.SupportedOU._65.toString())[1]));
            }

            result.add(matchOddBuilder.createMatchOdd());
        });

        return result;
    }

    private List<Match> getNewMatchesFromWebPage() {
        List<Match> result = Lists.newArrayList();
        LocalDateTime now = LocalDateTime.now();
        Map<String, Team> teamByName = teamDao.findAll().stream().collect(Collectors.toMap(Team::getName, Function.identity()));
        Map<String, String[]> newMatchesRaw = newMatchParser.parse(browser.doGet(MAIN_MATCHES_PAGE_LINK));

        for (Entry<String, String[]> entry : newMatchesRaw.entrySet()) {
            LocalDateTime startDate = DateUtil.getDate(entry.getValue()[2], entry.getValue()[3], entry.getValue()[4],
                    entry.getValue()[5], entry.getValue()[6]);
            long diffInSeconds = ChronoUnit.SECONDS.between(now, startDate);
            if (diffInSeconds >= SECONDS_INTERVAL_BEFORE_MATCH_TO_ADD_LOWER_BOUNDARY
                    && diffInSeconds < SECONDS_INTERVAL_BEFORE_MATCH_TO_ADD_UPPER_BOUNDARY) {
                Team homeTeam;
                Team awayTeam;
                if (teamByName.get(entry.getValue()[0]) != null) {
                    homeTeam = teamByName.get(entry.getValue()[0]);
                } else {
                    homeTeam = new Team(entry.getValue()[0]);
                    teamByName.put(homeTeam.getName(), homeTeam);
                }

                if (teamByName.get(entry.getValue()[1]) != null) {
                    awayTeam = teamByName.get(entry.getValue()[1]);
                } else {
                    awayTeam = new Team(entry.getValue()[1]);
                    teamByName.put(awayTeam.getName(), awayTeam);
                }
                result.add(new Match(entry.getKey(), homeTeam, awayTeam, startDate));
            }
        }
        return result;
    }


}
