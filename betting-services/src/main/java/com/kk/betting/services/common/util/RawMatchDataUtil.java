package com.kk.betting.services.common.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.kk.betting.domain.MatchOdd;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by KK on 2017-08-03.
 */
public class RawMatchDataUtil {

    private static final Log log = LogFactory.getLog(RawMatchDataUtil.class);
    private static final Map<String, MatchOdd.Type> CONSTANT_RESULT_EXPRESSIONS = ImmutableMap.<String, MatchOdd.Type>builder()
            .put("BTS YES", MatchOdd.Type.ODDBTSY)
            .put("BTS", MatchOdd.Type.ODDBTSY)
            .put("BTTS", MatchOdd.Type.ODDBTSY)
            .put("BTS NO", MatchOdd.Type.ODDBTSN)
            .put("1", MatchOdd.Type.ODD1)
            .put("2", MatchOdd.Type.ODD2)
            .put("X", MatchOdd.Type.ODDX)
            .put("1X", MatchOdd.Type.ODD1X)
            .put("12", MatchOdd.Type.ODD12)
            .put("X2", MatchOdd.Type.ODDX2)
            .build();

    private static final List<String> OVER_PATTERNS = ImmutableList.of("\\+\\s{0,}(\\d).(\\d)","OVER\\s{0,}(\\d).(\\d)", "OV.{0,1}\\s{0,}(\\d).(\\d)",
            "OV{0,1}\\s{0,}(\\d).(\\d)", "(\\d).(\\d)\\s{0,}OVER", "(\\d).(\\d)\\s{0,}OV\\.{0,}");
    private static final List<String> UNDER_PATTERNS = ImmutableList.of("\\-\\s{0,}(\\d).(\\d)","UNDER\\s{0,}(\\d).(\\d)", "UN.{0,1}\\s{0,}(\\d).(\\d)",
            "UN{0,1}\\s{0,}(\\d).(\\d)", "(\\d).(\\d)\\s{0,}UNDER", "(\\d).(\\d)\\s{0,}UN\\.{0,}");
    private static final List<String> EXACT_RESULT_PATTERNS = ImmutableList.of("(\\d):(\\d)", "(\\d)-(\\d)");

    public static MatchOdd.Type parseRawProposedMatchResult(String rawProposedResult) {
        String uc = rawProposedResult.trim().toUpperCase();

        if (CONSTANT_RESULT_EXPRESSIONS.containsKey(uc)) {
            return CONSTANT_RESULT_EXPRESSIONS.get(uc);
        }

        for (String overPattern : OVER_PATTERNS) {
            String over[] = RegexUtil.extractValuesFromRegex(overPattern, uc);
            if (over.length == 2) {
                return MatchOdd.Type.valueOf("ODDO" + over[0] + over[1]);
            }
        }
        for (String underPattern : UNDER_PATTERNS) {
            String under[] = RegexUtil.extractValuesFromRegex(underPattern, uc);
            if (under.length == 2) {
                return MatchOdd.Type.valueOf("ODDU" + under[0] + under[1]);
            }
        }

        for (String exactResultPattern : EXACT_RESULT_PATTERNS) {
            String exactResult[] = RegexUtil.extractValuesFromRegex(exactResultPattern, uc);
            if (exactResult.length == 2) {
                int homeScore = Integer.parseInt(exactResult[0]);
                int awayScore = Integer.parseInt(exactResult[1]);
                if (homeScore > awayScore) {
                    return MatchOdd.Type.ODD1;
                }
                if (homeScore < awayScore) {
                    return MatchOdd.Type.ODD2;
                } else {
                    return MatchOdd.Type.ODDX;
                }
            }
        }
        log.warn("Couldn't determie result type for input: " + rawProposedResult);
        return null;
    }
}
