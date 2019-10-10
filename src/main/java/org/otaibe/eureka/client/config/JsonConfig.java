package org.otaibe.eureka.client.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.vertx.core.json.Json;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
@Slf4j
public class JsonConfig {

    @Getter
    ObjectMapper objectMapper;

    @Produces
    public ObjectMapper objectMapper() {
        //log.info("producing object mapper");
        objectMapper = new ObjectMapper();
        fillObjectMapper(getObjectMapper());
        getObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true);
        //log.info("object mapper is initialized");
        return getObjectMapper();
    }

    @PostConstruct
    public void init() {
        fillObjectMapper(Json.mapper);
        fillObjectMapper(Json.prettyMapper);
    }

    private void fillObjectMapper(ObjectMapper objectMapper1) {

        // perform configuration
        objectMapper1
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        ;
        objectMapper1.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
