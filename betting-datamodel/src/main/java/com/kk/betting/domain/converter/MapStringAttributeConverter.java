package com.kk.betting.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Created by KK on 2017-09-02.
 */
@Converter(autoApply = true)
public class MapStringAttributeConverter implements AttributeConverter<String, Map<String, Object>> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public Map<String, Object> convertToDatabaseColumn(String attribute) {
        try {
            if (Objects.isNull(attribute)) return Maps.newHashMap();
            return Maps.newHashMap(OBJECT_MAPPER.readValue(attribute, new TypeReference<Map<String, Object>>() {
            }));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(Map<String, Object> dbData) {
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(dbData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
