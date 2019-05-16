package com.kk.betting.services.bettorhandling.typersi.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.kk.betting.services.bettorhandling.typersi.dto.TypersiMatchBetProposalDTO;
import com.kk.betting.services.common.service.HtmlParser;
import com.kk.betting.services.common.util.RegexUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by KK on 2017-08-02.
 */
public class TypersiBetProposalParser extends HtmlParser<List<TypersiMatchBetProposalDTO>> {

    private static final List<String> SUPPORTED_SPORTS = ImmutableList.of("Piłka nożna");
    private static final String BET_PROPOSAL_SPLITTER = "typer,";
    private static final String BETTOR_REGEX = ".*?html\">(.*?)<.*?";
    private static final String SINGLE_MATCH_DATA_REGEX = ".*?<td>(.*?)</td>.*?<td>(.*?)</td>.*?<td>(.*?)</td>.*?<td>(.*?)</td>.*?<td>(.*?)</td>.*?";
    private static final String PROPOSED_MATCH_RESULT_REGEX = ".*?>(.*?)</a>.*?";
    private static final String PROPOSED_MATCH_ODD_REGEX = ".*?>(.*?)</a>.*?";

    @Override
    public List<TypersiMatchBetProposalDTO> parse(String content) {
        List<String> proposals = Stream.of(content.split(BET_PROPOSAL_SPLITTER)).filter(l -> SUPPORTED_SPORTS.stream().filter(l::contains).findAny().isPresent()).collect(Collectors.toList());
        return proposals.stream().map(p -> rawDataToMatchProposalDTO(parseLine(p)))
                .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

    private List<String> parseLine(String proposalLine) {
        List<String> result = Lists.newArrayList();
        String[] bettor = RegexUtil.extractValuesFromRegex(BETTOR_REGEX, proposalLine);
        if (bettor.length == 1) {
            String[] allData = RegexUtil.extractValuesFromRegex(SINGLE_MATCH_DATA_REGEX, proposalLine);
            if (allData.length == 5) {
                String[] proposedResult = RegexUtil.extractValuesFromRegex(PROPOSED_MATCH_RESULT_REGEX, allData[2]);
                if (proposedResult.length == 1) {
                    String[] odd = RegexUtil.extractValuesFromRegex(PROPOSED_MATCH_ODD_REGEX, allData[4]);
                    if (odd.length == 1) {
                        result.add(bettor[0]);
                        result.add(allData[0]);
                        result.add(allData[1]);
                        result.add(proposedResult[0]);
                        result.add(odd[0]);
                    }
                }
            }
        }
        return result;
    }

    private Optional<TypersiMatchBetProposalDTO> rawDataToMatchProposalDTO(List<String> rawData) {
        if (rawData.size() != 5) {
            return Optional.empty();
        }
        TypersiMatchBetProposalDTO matchProposalDTO = new TypersiMatchBetProposalDTO();
        matchProposalDTO.setBettor(rawData.get(0));
        matchProposalDTO.setTime(rawData.get(1));
        matchProposalDTO.setMatch(rawData.get(2));
        matchProposalDTO.setProposedResult(rawData.get(3));
        matchProposalDTO.setOdd(rawData.get(4));
        return Optional.of(matchProposalDTO);
    }
}
