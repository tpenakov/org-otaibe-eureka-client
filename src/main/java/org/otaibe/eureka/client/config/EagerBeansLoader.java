package org.otaibe.eureka.client.config;

import io.quarkus.runtime.StartupEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * Class is used to eagerly load all required beans and will try to preserve the bean load logic
 * All beans that require such load should be added here as dependencies
 */
@ApplicationScoped
@Getter
@Setter
@Slf4j
public class EagerBeansLoader {
    @Inject
    org.otaibe.commons.quarkus.eureka.client.service.EurekaClient eurekaClient;

    public void init(@Observes StartupEvent event) {
        log.info("init start");
        getEurekaClient().registerApp(); //not enough just to inject bean ...
        log.info("init end");
    }
}
