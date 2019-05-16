package com.kk.betting.services.common.matcher.service.cleanse;

import javax.inject.Named;
import java.util.Optional;

/**
 * Created by KK on 2017-07-28.
 */
@Named
public class Cleanser {

    private CleanseFunction replaceSpecialSymbols = new ReplaceSpecialSymbols();
    private CleanseFunction removeSpecialSymbols = new RemoveSpecialSymbols();
    private CleanseFunction stripAccents = new StripAccents();
    private CleanseFunction upperCase = new UpperCase();
    private CleanseFunction replacePolishCitySynonyms = new ReplacePolishCitySynonyms();

    public String cleanse(String input) {
        return Optional.of(input)
                .flatMap(replaceSpecialSymbols::cleanse)
                .flatMap(removeSpecialSymbols::cleanse)
                .flatMap(stripAccents::cleanse)
                .flatMap(upperCase::cleanse)
                .flatMap(replacePolishCitySynonyms::cleanse)
                .get();
    }
}
