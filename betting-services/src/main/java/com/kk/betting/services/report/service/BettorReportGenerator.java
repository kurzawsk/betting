package com.kk.betting.services.report.service;

import com.kk.betting.services.common.service.HTMLTableBuilder;
import com.kk.betting.services.common.dao.BettingEventDao;
import com.kk.betting.services.common.dao.BettorDao;
import com.kk.betting.domain.BettingEvent;
import com.kk.betting.domain.Bettor;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by KK on 2017-07-01.
 */
@Named
public class BettorReportGenerator {

    public static final String NOT_AVAILABLE_SYMBOL = " - ";
    private static final long MAX_DAYS_AGO_TO_SHOW = 2L;

    @Inject
    private BettorDao bettorDao;

    @Inject
    private BettingEventDao bettingEventDao;


    public String generateBettingEventDiscrepanciesReport() {
        List<Object[]> discrepancies = bettingEventDao.getBettingEventDiscrepancies();
        if (discrepancies.isEmpty()) {
            return StringUtils.EMPTY;
        }

        StringBuilder content = new StringBuilder("Discrepancies in betting history");
        HTMLTableBuilder builder = new HTMLTableBuilder(null, true, 4);
        builder.addTableHeader("Bettor", "Match", "Event Time", "Match Start Time");

        for (Object[] discrepancy : discrepancies) {
            builder.addRowValues(discrepancy[0], discrepancy[1], discrepancy[2], discrepancy[3]);
        }

        content.append(builder.build()).append("<br><br>");
        return content.toString();

    }

    public String generateAllActiveBettorsHistoryReport() {
        List<? extends Bettor> bettors = bettorDao.getActiveBettors().stream()
                .sorted((b1,b2)->b2.getAvailableResources().compareTo(b1.getAvailableResources())).collect(Collectors.toList());
        StringBuilder content = new StringBuilder();
        content.append("Bettors activity history:<br>");

        for (Bettor bettor : bettors) {
            content.append("<b>Bettor: ").append(bettor.getId()).append("</b><br>");

            HTMLTableBuilder bettingActivityHistoryBuilder = new HTMLTableBuilder(null, true, 8);
            bettingActivityHistoryBuilder.addTableHeader("Event Time", "Event Type", "Match", "Resources Before", "Resources After", "Expected/Virtual<br> Match Result", "Odd", "Proposal Source");

            Predicate<BettingEvent> bettingEventsTimeFilter = be -> be.getEventTime().isAfter(LocalDateTime.now().minusDays(MAX_DAYS_AGO_TO_SHOW));
            List<BettingEvent> events = bettor.getBettingEvents().stream().filter(bettingEventsTimeFilter).collect(Collectors.toList());
            Collections.sort(events, (e1, e2) -> e2.getEventTime().compareTo(e1.getEventTime()));

            events.stream().forEach(event -> {
                String color = getColorFromBettingEventType(event.getType());
                String result = NOT_AVAILABLE_SYMBOL;
                if (event.getType() == BettingEvent.Type.MATCH_BET) {
                    result = event.getExpectedMatchResult().getLabel();
                } else if (event.getType() == BettingEvent.Type.MATCH_WITHDRAWN || event.getType() == BettingEvent.Type.BET_SUCCESSFUL || event.getType() == BettingEvent.Type.BET_UNSUCCESSFUL) {
                    result = event.getExpectedMatchResult().getLabel() + "/" + event.getMatch().getResult().toCleanFormat();
                }
                bettingActivityHistoryBuilder.addRowValues(event.getEventTime(), addColor(event.getEventType(), color), event.getMatch(), event.getResourcesAvailableBeforeEvent(),
                        event.getResourcesAvailableAfterEvent(), result,
                        ObjectUtils.defaultIfNull(event.getBetOdd(), NOT_AVAILABLE_SYMBOL), ObjectUtils.defaultIfNull(event.getProposalSource(), NOT_AVAILABLE_SYMBOL));

            });
            content.append(bettingActivityHistoryBuilder.build()).append("<br><br>");
        }

        return content.toString();

    }

    private String addColor(Object text, String color) {
        return "<span style=\"color:" + color + "\">" + text + "<span>";
    }

    private String getColorFromBettingEventType(BettingEvent.Type type) {

        switch (type) {
            case BET_SUCCESSFUL:
                return "Green";
            case MATCH_BET:
                return "DodgerBlue";
            case MONEY_WITHDRAWN:
                return "GreenYellow";
            case MONEY_ADDED:
                return "Yellow";
            case BET_UNSUCCESSFUL:
                return "Red";
            case MATCH_WITHDRAWN:
                return "Orange";
        }
        return null;
    }
}
