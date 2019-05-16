package com.kk.betting.services.matchdatacollection.sourcesystems.betexplorer.service;

import com.google.common.collect.Maps;
import com.kk.betting.services.common.util.RegexUtil;
import com.kk.betting.services.common.service.WebPageBrowser;
import com.kk.betting.services.common.service.HtmlParser;

import java.util.Collections;
import java.util.Map;

/**
 * Created by KK on 2017-02-19.
 */
public class NewMatchParser extends HtmlParser<Map<String, String[]>> {

    private static final String PATTERN_TEAM_NAMES = ".*?table-matches__time\">(.*?)</span><a href=\"(.*?)/\">(.*?) - (.*?)</a></td>.*?";
    private static final String PATTERN_MATCH_DAY = ".*?\"table-matches__date\">(.*?)</th></tr>.*?";

    @Override
    public Map<String, String[]> parse(String content) {
        Map<String, String[]> result = Maps.newHashMap();
        String currentMatchDay = null;
        for (String line : content.split("\n")) {
            String[] matchValues = RegexUtil.extractValuesFromRegex(PATTERN_TEAM_NAMES, line);
            String[] matchDay = RegexUtil.extractValuesFromRegex(PATTERN_MATCH_DAY, line);

            if (matchDay.length == 1) {
                currentMatchDay = matchDay[0];
            }

            if (matchValues.length == 4 && currentMatchDay != null) {
                String[] timeArray = matchValues[0].split(":");
                String[] dateArray = currentMatchDay.split("\\.");
                String matchIdentifier = matchValues[1].substring(matchValues[1].lastIndexOf("/") + 1,
                        matchValues[1].length());
                result.put(matchIdentifier,new String[]{matchValues[2],matchValues[3],dateArray[0],dateArray[1],dateArray[2],timeArray[0],timeArray[1]});
            }



        }
        return result;
    }

    public static void  main(String[] main){
        WebPageBrowser browser = new WebPageBrowser();
        NewMatchParser p = new NewMatchParser();
        Map<String, String[]> result = p.parse(browser.doGet("http://www.betexplorer.com/odds-filter/soccer/?rangeFrom=1&rangeTo=999&days=7", Collections.EMPTY_MAP));
        int x = 0;
    }
}
