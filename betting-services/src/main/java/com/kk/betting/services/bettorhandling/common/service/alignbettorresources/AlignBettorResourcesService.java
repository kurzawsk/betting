package com.kk.betting.services.bettorhandling.common.service.alignbettorresources;

import com.kk.betting.domain.Bettor;
import com.kk.betting.dto.AlignBettorResourcesEventDTO;
import com.kk.betting.services.common.dao.BettorDao;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by KK on 2017-09-11.
 */
public class AlignBettorResourcesService {

    @Inject
    private BettorDao bettorDao;

    @Inject
    private AlignBettorResourcesEventSender alignBettorResourcesEventSender;


    public void withDrawMoney() {
        List<? extends Bettor> bettors = bettorDao.findAll();
        // >0 we added money to bettor, <0 we got money from bettor, we earned sth!
        bettors.stream().forEach(bettor -> {
            BigDecimal changeAmount = bettor.getDefaultMonthStartAmount().subtract(bettor.getAvailableResources());
            AlignBettorResourcesEventDTO alignBettorResourcesEventDTO = new AlignBettorResourcesEventDTO();
            alignBettorResourcesEventDTO.setBettorId(bettor.getId());
            alignBettorResourcesEventDTO.setAmount(changeAmount);
            alignBettorResourcesEventSender.sendMessage(alignBettorResourcesEventDTO);
        });
    }

}
