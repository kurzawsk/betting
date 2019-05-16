package com.kk.betting.services.bettorhandling.common.service;

import com.kk.betting.services.common.service.Converter;
import com.kk.betting.domain.Bettor;
import com.kk.betting.dto.BettorDTO;
import org.json.JSONObject;

import javax.inject.Named;

/**
 * Created by KK on 2017-07-13.
 */
@Named
public class BettorConverter implements Converter<Bettor,BettorDTO> {
    @Override
    public BettorDTO convertToDTO(Bettor domain) {
        BettorDTO bettorDTO = new BettorDTO();
        bettorDTO.setId(domain.getId());
        bettorDTO.setAvailableResources(domain.getAvailableResources());
        bettorDTO.setTotalProfit(domain.getTotalProfit());
        bettorDTO.setDescription(domain.getDescription());
        bettorDTO.setBookmaker(domain.getBookmaker().getLabel());
        bettorDTO.setAvgResourcesSinceLastAlignment(domain.getAvgResourcesSinceLastAlignment());
        bettorDTO.setParameters(new JSONObject(domain.getLogicParameters()).toString(2));
        bettorDTO.setActive(domain.isActive());
        return bettorDTO;
    }

    @Override
    public Bettor convertToDomain(BettorDTO dto) {
        throw new UnsupportedOperationException();
    }
}
