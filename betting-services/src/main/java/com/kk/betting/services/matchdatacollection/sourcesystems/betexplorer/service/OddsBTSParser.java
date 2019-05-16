package com.kk.betting.services.matchdatacollection.sourcesystems.betexplorer.service;

import com.google.common.collect.Maps;
import com.kk.betting.services.common.util.RegexUtil;
import com.kk.betting.services.common.service.HtmlParser;
import com.kk.betting.services.matchdatacollection.sourcesystems.betexplorer.dao.BetExplorerCollector;

import java.util.Map;

/**
 * Created by KK on 2017-02-15.
 */
public class OddsBTSParser extends HtmlParser<Map<String, String[]>> {

    private static final String BOOKMAKER_PATTERN = ".*?over-s-only.\"><a href=.\"./bookmaker./(.*?)./.*?";
    private static final String AVERAGE_PATTERN = ".*?only.\" colspan=.\"\\d.\">(Average odds).*?";
    private static final String ODDS_VALUE_PATTERN = ".*?table-main__odds.*?data-odd=.\"(.*?).\".*?";

    @Override
    public Map<String, String[]> parse(String content) {
        Map<String, String[]> result = Maps.newHashMap();
        String[] lines = content.split("td>");

        for (int i = 0; i < lines.length - 4; i++) {

            String[] bookmaker = RegexUtil.extractValuesFromRegex(BOOKMAKER_PATTERN, lines[i]);
            String[] average = RegexUtil.extractValuesFromRegex(AVERAGE_PATTERN, lines[i]);
            if (bookmaker.length == 1) {
                String[] oddOYes = RegexUtil.extractValuesFromRegex(ODDS_VALUE_PATTERN, lines[i + 4]);
                String[] oddNo = RegexUtil.extractValuesFromRegex(ODDS_VALUE_PATTERN, lines[i + 5]);

                if (oddOYes.length == 1 && oddNo.length == 1) {
                    result.put(bookmaker[0], new String[]{oddOYes[0], oddNo[0]});
                }
            }

            if (average.length == 1) {
                String[] oddOYes = RegexUtil.extractValuesFromRegex(ODDS_VALUE_PATTERN, lines[i + 2]);
                String[] oddNo = RegexUtil.extractValuesFromRegex(ODDS_VALUE_PATTERN, lines[i + 3]);
                if (oddOYes.length == 1 && oddNo.length == 1) {
                    result.put(BetExplorerCollector.AVERAGE_NAME, new String[]{oddOYes[0], oddNo[0]});
                }
            }
        }

        return result;
    }

}
