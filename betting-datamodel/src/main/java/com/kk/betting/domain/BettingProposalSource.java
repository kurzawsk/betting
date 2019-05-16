package com.kk.betting.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.IOException;
import java.util.Map;

/**
 * Created by KK on 2017-05-20.
 */

@Entity
@Table(name = "BETTING_PROPOSAL_SOURCE")
public class BettingProposalSource extends ManagedEntityImp {

    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean isActive = true;

    @Column(name = "LOGIC_IMP_CLASS", nullable = false)
    private String logicImpClass;

    @Column(name = "SCHEDULE_EXPRESSION", nullable = false)
    private String scheduleExpression;


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getLogicImpClass() {
        return logicImpClass;
    }

    public void setLogicImpClass(String logicImpClass) {
        this.logicImpClass = logicImpClass;
    }

    public String getScheduleExpression() {
        return scheduleExpression;
    }

    public void setScheduleExpression(String scheduleExpression) {
        this.scheduleExpression = scheduleExpression;
    }

}
