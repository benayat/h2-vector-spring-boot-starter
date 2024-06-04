package org.benaya.ai.inmemoryh2vectorspringbootstarter.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Converter(autoApply = true)
@Component
public class FloatArrayToStringConverter implements AttributeConverter<float[], String> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(float[] attribute) {
        if (attribute == null) {
            return null;
        }
        return Arrays.toString(attribute);
    }

    @Override
    public float[] convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new float[0];
        }
        try {
            return objectMapper.readValue(dbData, float[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

