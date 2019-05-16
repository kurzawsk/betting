package com.kk.betting.services.common.matcher.service.cleanse;

import java.util.Optional;

/**
 * Created by KK on 2017-07-28.
 */
public class RemoveSpecialSymbols implements CleanseFunction {

    public static final String SPECIAL_SYMBOLS = "!\"#$%'()*+,./:;<=>?@[\\]^_`{|}~\u007F";

    @Override
    public Optional<String> cleanse(String input) {
        for (char c : SPECIAL_SYMBOLS.toCharArray()) {
            input = input.replace("" + c, "");
        }
        return Optional.of(removeAdditionalSpaces(input));
    }

}
