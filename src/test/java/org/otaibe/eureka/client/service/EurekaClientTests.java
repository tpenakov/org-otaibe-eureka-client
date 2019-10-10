package org.otaibe.eureka.client.service;

import io.quarkus.test.junit.QuarkusTest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Map;

@QuarkusTest
@Getter
@Setter
@Slf4j
public class EurekaClientTests {

    @Inject
    EurekaClient eurekaClient;

    @Test
    public void getAllAppsTest() {
        Map<String, Object> allApps = getEurekaClient()
                .getAllApps()
                .block();
        log.info("all apps: {}", allApps);
        Assertions.assertNotNull(allApps);
        Assertions.assertTrue(allApps.keySet().size() > 0);
    }

    @Test
    public void registerAppTest() {
        Boolean status = getEurekaClient()
                .registerApp()
                .block();
        log.info("registration status: {}", status);
        Assertions.assertTrue(status);
    }
}
