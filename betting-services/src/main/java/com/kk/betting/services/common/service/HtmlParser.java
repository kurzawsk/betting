package com.kk.betting.services.common.service;

/**
 * Created by KK on 2017-02-15.
 */
public abstract class HtmlParser<T> {

    public abstract T parse(String content);

}
