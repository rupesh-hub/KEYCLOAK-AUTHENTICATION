package com.keycloak.auth.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class ConfigurationTokenResourceConfiguration {

    private final String longLivedTokenRole;

    public static ConfigurationTokenResourceConfiguration readFromEnvironment() {
        return new ConfigurationTokenResourceConfiguration(readLongLivedRoleFromEnvironment());
    }

    private static String readLongLivedRoleFromEnvironment() {
        String roleForLongLivedTokens = System.getenv("KEYCLOAK_LONG_LIVED_ROLE_NAME");
        if (roleForLongLivedTokens == null || roleForLongLivedTokens.trim().isEmpty())
            return "long_lived_token";
        return roleForLongLivedTokens;
    }
}
