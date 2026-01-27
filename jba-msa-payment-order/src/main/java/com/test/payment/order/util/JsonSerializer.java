package com.test.payment.order.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.payment.order.infrastructure.exception.custom.ParsingJsonException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@UtilityClass
public class JsonSerializer {

    private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    public <T> T jsonStringToObject(
            @NonNull String jsonString,
            @NonNull Class<T> desiredClass
    ) {
        if (jsonString.isBlank()) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(jsonString, desiredClass);
        } catch (JsonProcessingException error) {
            log.error("ERROR JsonSerializer jsonStringToObject error {}", error.getMessage());
            throw new ParsingJsonException("Error");
        }
    }
}
