package com.keycloak.auth.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;
import org.keycloak.common.util.Time;

import java.util.Optional;

@Setter
public class TokenConfiguration {

    @JsonProperty("tokenLifespanInSeconds")
    private Integer tokenLifespanInSeconds;

    public int computeTokenExpiration(int maxExpiration, boolean longLivedTokenAllowed) {
        return Optional.ofNullable(tokenLifespanInSeconds)
                .map(lifespan -> Time.currentTime() + lifespan)
                .map(requestedExpiration -> longLivedTokenAllowed ? requestedExpiration : Math.min(maxExpiration, requestedExpiration))
                .orElse(maxExpiration).intValue();
    }

}
