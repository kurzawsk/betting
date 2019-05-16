package com.kk.betting.domain;

import com.kk.betting.domain.converter.MapStringAttributeConverter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.IOException;
import java.util.Map;

/**
 * Created by KK on 2017-09-02.
 */

@Entity
@Table(name = "CUSTOM_JOB")
public class CustomJob extends ManagedEntityImp {

    @Column(name = "ACTIVE", nullable = false)
    private boolean active = true;

    @Column(name = "LOGIC_IMP_CLASS", nullable = false)
    private String logicImpClass;

    @Column(name = "SCHEDULE_EXPRESSION", nullable = false)
    private String scheduleExpression;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Column(name = "LOGIC_PARAMETERS", length = 4000)
    private String logicParametersSerialized;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getLogicParameters() {
        return new MapStringAttributeConverter().convertToDatabaseColumn(logicParametersSerialized);
    }

    public void setLogicParameters(Map<String, Object> logicParameters) throws IOException {
        this.logicParametersSerialized = new MapStringAttributeConverter().convertToEntityAttribute(logicParameters);
    }
}
