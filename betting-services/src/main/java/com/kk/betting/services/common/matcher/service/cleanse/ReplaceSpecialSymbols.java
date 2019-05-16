package com.kk.betting.services.common.matcher.service.cleanse;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Optional;

/**
 * Created by KK on 2017-07-28.
 */
public class ReplaceSpecialSymbols implements CleanseFunction {

    public static final Map<String, String> DICTIONARY = ImmutableMap.<String, String>builder()
            .put("&", " AND ")
            .put("-", " ")
            .build();


    @Override
    public Optional<String> cleanse(String input) {
        for (Map.Entry<String, String> e : DICTIONARY.entrySet()) {
            input = input.replace(e.getKey(), e.getValue());

        }
        return Optional.of(removeAdditionalSpaces(input));
    }
}
