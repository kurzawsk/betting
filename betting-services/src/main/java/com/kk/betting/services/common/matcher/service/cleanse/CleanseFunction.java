package com.kk.betting.services.common.matcher.service.cleanse;

import java.util.Optional;

/**
 * Created by KK on 2017-07-28.
 */
@FunctionalInterface
public interface CleanseFunction {
     default String removeAdditionalSpaces(String input){
          return input.trim().replaceAll("\\s+", " ");
     }
     Optional<String> cleanse(String input);
}
