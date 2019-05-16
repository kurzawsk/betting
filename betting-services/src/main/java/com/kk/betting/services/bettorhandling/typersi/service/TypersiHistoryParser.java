package com.kk.betting.services.bettorhandling.typersi.service;

import com.google.common.collect.Lists;
import com.kk.betting.services.bettorhandling.typersi.dto.TypersiBettorHistoryEntryDTO;
import com.kk.betting.services.common.service.HtmlParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by KK on 2017-08-30.
 */
public class TypersiHistoryParser extends HtmlParser<List<TypersiBettorHistoryEntryDTO>> {

    private static final String TBODY = "tbody";
    private static final String TR = "tr";
    private static final String TD = "td";

    @Override
    public List<TypersiBettorHistoryEntryDTO> parse(String content) {
        List<TypersiBettorHistoryEntryDTO> result = Lists.newArrayList();

        Document document = Jsoup.parse(content);
        Element table = document.select(TBODY).first();
        Elements rows = table.select(TR);
        rows.stream().forEach(e -> {
            Elements cols = e.select(TD);
            if (cols.size() == 7) {
                TypersiBettorHistoryEntryDTO dto = new TypersiBettorHistoryEntryDTO();
                dto.setBettor(cols.get(0).text());
                dto.setWonBets(cols.get(2).text());
                dto.setLostBets(cols.get(3).text());
                dto.setAvgOdd(cols.get(5).text());
                dto.setProfit(cols.get(6).text());
                result.add(dto);
            }
        });
        return result;
    }

}
