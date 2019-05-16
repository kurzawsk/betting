package com.kk.betting.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.YearMonth;

/**
 * Created by KK on 2017-08-30.
 */


@Converter(autoApply = true)
public class YearMonthAttributeConverter implements AttributeConverter<YearMonth, String> {

    private static final String SPLITTER = "-";

    @Override
    public String convertToDatabaseColumn(java.time.YearMonth attribute) {
        if (attribute == null)  return null ;
        return attribute.getYear() + SPLITTER + attribute.getMonthValue();
    }

    @Override
    public java.time.YearMonth convertToEntityAttribute(String dbData) {
        if (dbData == null)  return null ;
        String[] yearMon = dbData.split(SPLITTER);
        return YearMonth.of(Integer.valueOf(yearMon[0]),Integer.valueOf(yearMon[1]));
    }
}