package com.keycloak.demo.embadded;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({KeycloakProperties.class, KeycloakCustomProperties.class})
@ComponentScan(basePackageClasses = EmbeddedKeycloakConfig.class)
public class EmbeddedSpringKeycloakAutoConfiguration {
}
