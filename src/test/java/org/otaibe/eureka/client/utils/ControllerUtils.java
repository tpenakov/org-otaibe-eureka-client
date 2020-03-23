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

    @ConfigProperty(name = "service.http.host")
    Optional<URI> host;

    public String getBasePath() {
        return getHost()
                .map(uri -> UriBuilder.fromUri(uri))
                .orElseGet(() -> UriBuilder.fromPath(StringUtils.EMPTY))
                .toTemplate();
    }

}
