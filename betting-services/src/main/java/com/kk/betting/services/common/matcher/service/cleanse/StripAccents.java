package com.kk.betting.services.common.matcher.service.cleanse;

import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Created by KK on 2017-07-28.
 */
public class StripAccents implements CleanseFunction {
    @Override
    public Optional<String> cleanse(String input) {
        return Optional.of(StringUtils.stripAccents(input));
    }
}
