package com.kk.betting.services.common.matcher.service.cleanse;

import java.util.Optional;

/**
 * Created by KK on 2017-07-28.
 */
public class UpperCase implements CleanseFunction {
    @Override
    public Optional<String> cleanse(String input) {
        return Optional.of(input.toUpperCase());
    }
}
