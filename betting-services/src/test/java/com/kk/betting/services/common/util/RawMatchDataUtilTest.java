package com.kk.betting.services.common.util;

import com.kk.betting.domain.MatchOdd;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by KK on 2017-08-07.
 */
public class RawMatchDataUtilTest {

    @Test
    public void testParseProposedMatchResult() {

        assertEquals(MatchOdd.Type.ODDO25,RawMatchDataUtil.parseRawProposedMatchResult("Over 2.5"));
        assertEquals(MatchOdd.Type.ODDO35,RawMatchDataUtil.parseRawProposedMatchResult("over 3.5"));
        assertEquals(MatchOdd.Type.ODDU25,RawMatchDataUtil.parseRawProposedMatchResult("under 2.5"));
        assertEquals(MatchOdd.Type.ODDO35,RawMatchDataUtil.parseRawProposedMatchResult("\tov. 3,5"));
        assertEquals(MatchOdd.Type.ODDU55,RawMatchDataUtil.parseRawProposedMatchResult("\tun 5,5"));
        assertEquals(MatchOdd.Type.ODDBTSN,RawMatchDataUtil.parseRawProposedMatchResult("\t BTS NO "));
        assertEquals(MatchOdd.Type.ODDO25,RawMatchDataUtil.parseRawProposedMatchResult("2,5 OVER"));
        assertEquals(MatchOdd.Type.ODDU45,RawMatchDataUtil.parseRawProposedMatchResult("4.5 un"));
        assertEquals(MatchOdd.Type.ODDU45,RawMatchDataUtil.parseRawProposedMatchResult("4.5 un."));
        assertEquals(MatchOdd.Type.ODDO45,RawMatchDataUtil.parseRawProposedMatchResult("+4,5"));
        assertEquals(MatchOdd.Type.ODDU25,RawMatchDataUtil.parseRawProposedMatchResult("- 2,5"));

    }
}
