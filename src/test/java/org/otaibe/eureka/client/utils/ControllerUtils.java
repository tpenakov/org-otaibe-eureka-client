package org.otaibe.eureka.client.utils;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Optional;

@ApplicationScoped
@Getter
public class ControllerUtils {

    @ConfigProperty(name = "quarkus.servlet.context-path", defaultValue = StringUtils.EMPTY)
    String contextPath;
    @ConfigProperty(name = "service.http.host")
    Optional<URI> host;

    public String getBasePath() {
        return getHost()
                .map(uri -> UriBuilder.fromUri(uri).path(getContextPath()))
                .orElseGet(() -> UriBuilder.fromPath(getContextPath()))
                .toTemplate();
    }

}
