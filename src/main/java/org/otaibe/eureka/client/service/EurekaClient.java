package org.otaibe.eureka.client.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;

/**
 * based on:
 * https://github.com/Netflix/eureka/wiki/Eureka-REST-operations
 */

@ApplicationScoped
@Getter
@Setter
@Slf4j
public class EurekaClient {

    @Inject
    org.otaibe.commons.quarkus.eureka.client.service.EurekaClient client;

    public Mono<Map<String, Object>> getAllApps() {
        /**
         * curl -v -H 'Accept: application/json' http://eureka-at-staging.otaibe.org:9333/eureka/apps
         */
        return getClient().getAllApps();
    }

    public Mono<String> getNextServer(String serviceName) {
        /**
         * curl -v -H 'Accept: application/json' http://eureka-at-staging.otaibe.org:9333/eureka/apps/{APP_ID}
         */
        return getClient().getNextServer(serviceName);
    }

    public Mono<Boolean> registerApp() {
        return getClient().registerApp();
    }

}
