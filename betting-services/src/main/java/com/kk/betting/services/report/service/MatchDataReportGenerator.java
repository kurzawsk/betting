package com.kk.betting.services.report.service;

import com.kk.betting.services.common.service.HTMLTableBuilder;
import com.kk.betting.services.common.dao.MatchDao;
import com.kk.betting.domain.Match;
import com.kk.betting.domain.MatchResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
public class MatchDataReportGenerator {

    public static final Log log = LogFactory.getLog(MatchDataReportGenerator.class);

    @Inject
    private MatchDao matchDao;

    public String createPeriodMatchReport(LocalDateTime from, LocalDateTime to) {
        List<? extends Match> matches = matchDao.getMatchesStartingBetween(from, to);

        StringBuilder content = new StringBuilder();

        Map<MatchResult, List<Match>> resultMap = matches.stream().collect(Collectors.groupingBy(Match::getResult));
        content.append("Matches by result:<br>");
        HTMLTableBuilder matchesByResultBuilder = new HTMLTableBuilder(null, true, 2);
        matchesByResultBuilder.addTableHeader("Result", "Number of matches");
        resultMap.forEach((r, m) -> matchesByResultBuilder.addRowValues(r.asType() == MatchResult.Type.NORMAL ? r.toScores() : r.asType(), m.size()));
        content.append(matchesByResultBuilder.build()).append("<br><br>");


        List<Match> abnormallyFinishedMatches = matches.stream().filter(m -> MatchResult.Type.NO_SCORES.contains(m.getResult().asType())).collect(Collectors.toList());
        content.append("Matches which have not finished normally:<br>");
        HTMLTableBuilder matchDetailsBuilder = new HTMLTableBuilder(null, true, 5);
        matchDetailsBuilder.addTableHeader("Identifier", "Home team", "Away team", "Start time", "Result");
        abnormallyFinishedMatches.stream().forEach(m -> matchDetailsBuilder.addRowValues(m.getIdentifier(), m.getHomeTeam(), m.getAwayTeam(), m.getStartTime(), m.getResult().asType()));
        content.append(matchDetailsBuilder.build()).append("<br><br>");

        return content.toString();
    }

    public String createDailyMatchReport(LocalDate day) {

        LocalDateTime from = day.atStartOfDay();
        LocalDateTime to = day.atStartOfDay().withHour(23).withMinute(59).withSecond(59);
        return createPeriodMatchReport(from, to);

    }

}
