package com.keycloak.auth.support;

import lombok.RequiredArgsConstructor;
import org.keycloak.common.Profile;
import org.keycloak.common.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class EmbeddedKeycloakServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedKeycloakServer.class);
    private final ServerProperties serverProperties;

    @Bean
    public ApplicationListener<ApplicationReadyEvent> onApplicationReadyEventListener() {

        return (evt) -> {

            LOGGER.info("Using Keycloak Version: %s", Version.VERSION_KEYCLOAK);
            LOGGER.info("Enabled Keycloak Features (Deprecated): %s", Profile.getDeprecatedFeatures());
            LOGGER.info("Enabled Keycloak Features (Preview): %s", Profile.getPreviewFeatures());
            LOGGER.info("Enabled Keycloak Features (Experimental): %s", Profile.getExperimentalFeatures());
            LOGGER.info("Enabled Keycloak Features (Disabled): %s", Profile.getDisabledFeatures());

            Integer port = serverProperties.getPort();

            LOGGER.info("Embedded Keycloak started: Browse to <http://localhost:%d%s> to use keycloak%n", port, serverProperties.getServlet().getContextPath());
        };
    }

}
