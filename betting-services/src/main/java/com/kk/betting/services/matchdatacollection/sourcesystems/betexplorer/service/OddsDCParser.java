package com.kk.betting.services.matchdatacollection.sourcesystems.betexplorer.service;

import com.kk.betting.services.common.service.HtmlParser;

import java.util.Map;

/**
 * Created by KK on 2017-03-02.
 */
public class OddsDCParser extends HtmlParser<Map<String, String[]>> {

    private Odds1X2Parser delegate = new  Odds1X2Parser();
    @Override
    public Map<String, String[]> parse(String content) {
        return delegate.parse(content);
    }
}
