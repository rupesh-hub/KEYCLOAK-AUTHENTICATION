package com.keycloak;

import com.keycloak.auth.support.EmbeddedKeycloakConfig;
import com.keycloak.auth.support.KeycloakCustomProperties;
import com.keycloak.auth.support.KeycloakProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
@EnableConfigurationProperties({KeycloakProperties.class, KeycloakCustomProperties.class})
@ComponentScan(basePackageClasses = EmbeddedKeycloakConfig.class, basePackages = {"com.keycloak.*"})
@EntityScan({"com.keycloak.model"})
public class KeycloakDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeycloakDemoApplication.class, args);
	}

}
