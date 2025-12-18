package com.example.vms.Version;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class VersionJsonConverter implements AttributeConverter<Version, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Version version) {
        if (version == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(version);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Version to JSON", e);
        }
    }

    @Override
    public Version convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, Version.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error reading Version from JSON", e);
        }
    }
}
