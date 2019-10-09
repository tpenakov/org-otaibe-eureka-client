package org.otaibe.eureka.client.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * Created by triphon on 24.03.16.
 */
@ApplicationScoped
@Slf4j
public class JsonUtils {
    public <T> T fromMap(Map input, ObjectMapper objectMapper, Class<T> outputClass) {
        try {
            T value = objectMapper.readValue(objectMapper.writeValueAsBytes(input), outputClass);
            return value;
        } catch (IOException e) {
            log.error("unable to transform to outputClass", e);
            throw new RuntimeException(e);
        }

    }

    public Map toMap(Object input, ObjectMapper objectMapper) {
        try {
            Map value = objectMapper.readValue(objectMapper.writeValueAsBytes(input), Map.class);
            return value;
        } catch (IOException e) {
            log.error("uanble to transform to Map", e);
            throw new RuntimeException(e);
        }
    }

    public <T> T deepClone(final T input, ObjectMapper objectMapper, Class<T> resultType) throws Exception {
        if (input == null) {
            return null;
        }
        return objectMapper.readValue(objectMapper.writeValueAsBytes(input), resultType);
    }

    public Object toStringLazy(Object input, ObjectMapper objectMapper) {
        return new ToStringLazy(input, objectMapper);
    }

    public <T> Optional<T> readValue(String value, Class<T> clazz, ObjectMapper objectMapper1) {
        return Optional.ofNullable(objectMapper1)
                .map(objectMapper -> {
                    try {
                        return objectMapper.readValue(value, clazz);
                    } catch (IOException e) {
                        log.error("unable to deserialize", e);
                    }
                    return null;
                });
    }

    @AllArgsConstructor
    private static class ToStringLazy {
        private Object input;
        private ObjectMapper objectMapper;

        @Override
        public String toString() {
            if (objectMapper == null || input == null) {
                return StringUtils.EMPTY;
            }
            try {
                String value = objectMapper.writeValueAsString(input);
                return value;
            } catch (Exception e) {
                log.error("unable to serialize to json", e);
                return StringUtils.EMPTY;
            }
        }
    }

}
