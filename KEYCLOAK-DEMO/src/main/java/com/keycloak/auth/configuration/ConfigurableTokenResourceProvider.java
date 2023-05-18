package com.keycloak.auth.configuration;

import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.spi.HttpRequest;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.crypto.SignatureProvider;
import org.keycloak.crypto.SignatureVerifierContext;
import org.keycloak.events.EventBuilder;
import org.keycloak.models.*;
import org.keycloak.protocol.oidc.TokenManager;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.services.ErrorResponse;
import org.keycloak.services.Urls;
import org.keycloak.services.managers.AppAuthManager;
import org.keycloak.services.managers.AuthenticationManager;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resources.Cors;
import org.keycloak.services.util.DefaultClientSessionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class ConfigurableTokenResourceProvider implements RealmResourceProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurableTokenResourceProvider.class);
    private final KeycloakSession keycloakSession;
    private final TokenManager tokenManager;
    private final ConfigurationTokenResourceConfiguration configuration;

    @Override
    public Object getResource() {
        return this;
    }

    @Override
    public void close() {
    }

    @OPTIONS
    public Response preflight(@Context HttpRequest request) {
        return Cors.add(request, Response.ok()).auth().preflight().allowedMethods(new String[]{"POST", "OPTIONS"}).build();
    }

    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response createToken(TokenConfiguration tokenConfiguration, @Context HttpRequest httpRequest) {
        try {
            AccessToken accessToken = validateTokenAndUpdateSession(httpRequest);
            UserSessionModel userSessionModel = findSession();
            AccessTokenResponse accessTokenResponse = createAccessToken(userSessionModel, accessToken, tokenConfiguration);
            return buildCorsResponse(httpRequest, accessTokenResponse);
        } catch (ConfigurableTokenException e) {
            LOGGER.error("An error occurred when fetching an access token. {}", e.getMessage());
            return ErrorResponse.error(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    private AccessToken validateTokenAndUpdateSession(HttpRequest httpRequest) throws ConfigurableTokenException {
        RealmModel realmModel = keycloakSession.getContext().getRealm();
        String tokenString = readAccessTokenFrom(httpRequest);
        TokenVerifier<AccessToken> verifier = TokenVerifier.create(tokenString, AccessToken.class).withChecks(new TokenVerifier.Predicate[]{TokenVerifier.IS_ACTIVE, (TokenVerifier.Predicate) new TokenVerifier.RealmUrlCheck(

                Urls.realmIssuer(keycloakSession.getContext().getUri().getBaseUri(), realmModel.getName()))});

        try {
            SignatureVerifierContext verifierContext = (keycloakSession.getProvider(SignatureProvider.class, verifier.getHeader().getAlgorithm().name())).verifier(verifier.getHeader().getKeyId());
            verifier.verifierContext(verifierContext);
            AccessToken accessToken = verifier.verify().getToken();
            if (!tokenManager.checkTokenValidForIntrospection(keycloakSession, realmModel, accessToken))
                throw new VerificationException("introspection_failed");

            return accessToken;
        } catch (Exception e) {
            LOGGER.warn("Keycloak-ConfigurableToken: introspection of token failed", e);
            throw new ConfigurableTokenException("access_token_introspection_failed: " + e.getMessage());
        }
    }


    private AccessTokenResponse createAccessToken(UserSessionModel userSessionModel, AccessToken accessToken, TokenConfiguration tokenConfiguration) {
        RealmModel realmModel = keycloakSession.getContext().getRealm();
        ClientModel clientModel = realmModel.getClientByClientId(accessToken.getIssuedFor());
        LOGGER.info("Configurable token requested for username=%s and client=%s on realm=%s", userSessionModel.getUser().getUsername(), clientModel.getClientId(), realmModel.getName());
        AuthenticatedClientSessionModel clientSession = userSessionModel.getAuthenticatedClientSessionByClient(clientModel.getClientId());
        DefaultClientSessionContext defaultClientSessionContext = DefaultClientSessionContext.fromClientSessionScopeParameter(clientSession, keycloakSession);
        AccessToken newToken = tokenManager.createClientAccessToken(keycloakSession, realmModel, clientModel, userSessionModel.getUser(), userSessionModel, (ClientSessionContext) defaultClientSessionContext);
        updateTokenExpiration(newToken, tokenConfiguration, userSessionModel.getUser());
        return buildResponse(realmModel, userSessionModel, clientModel, clientSession, newToken);
    }

    private UserSessionModel findSession() throws ConfigurableTokenException {
        RealmModel realmModel = keycloakSession.getContext().getRealm();
        AuthenticationManager.AuthResult authResult = (new AppAuthManager()).authenticateBearerToken(keycloakSession, realmModel);
        if (!Objects.nonNull(authResult))
            throw new ConfigurableTokenException("not_authenticated");

        UserModel userModel = authResult.getUser();
        if (!Objects.nonNull(userModel) || !userModel.isEnabled())
            throw new ConfigurableTokenException("invalid_user");

        UserSessionModel userSession = authResult.getSession();
        if (!Objects.nonNull(userSession))
            throw new ConfigurableTokenException("missing_user_session");

        return userSession;
    }

    private Response buildCorsResponse(HttpRequest httpRequest, AccessTokenResponse accessTokenResponse) {
        return Cors
                .add(httpRequest)
                .auth()
                .allowedMethods(new String[]{"POST"})
                .auth()
                .exposedHeaders(new String[]{"Access-Control-Allow-Methods", "Access-Control-Allow-Origin"})
                .allowAllOrigins()
                .builder(Response.ok(accessTokenResponse)
                        .type(MediaType.APPLICATION_JSON_TYPE))
                .build();
    }

    private String readAccessTokenFrom(HttpRequest request) throws ConfigurableTokenException {
        return Optional.ofNullable(request.getHttpHeaders().getHeaderString("Authorization"))
                .filter(auth -> auth.startsWith("Bearer "))
                .map(auth -> auth.substring(7))
                .filter(token -> !token.isEmpty())
                .orElseThrow(() -> new ConfigurableTokenException("missing_access_token"));
    }


    private AccessTokenResponse buildResponse(RealmModel realmModel, UserSessionModel userSessionModel, ClientModel clientModel, AuthenticatedClientSessionModel clientSession, AccessToken newToken) {
        EventBuilder eventBuilder = new EventBuilder(realmModel, keycloakSession, keycloakSession.getContext().getConnection());
        DefaultClientSessionContext defaultClientSessionContext = DefaultClientSessionContext.fromClientSessionScopeParameter(clientSession, keycloakSession);
        return tokenManager.responseBuilder(realmModel, clientModel, eventBuilder, keycloakSession, userSessionModel, (ClientSessionContext) defaultClientSessionContext)
                .accessToken(newToken)
                .build();
    }

    private void updateTokenExpiration(AccessToken newToken, TokenConfiguration tokenConfiguration, UserModel user) {
        Objects.requireNonNull(user);
        boolean longLivedTokenAllowed = Optional.of(keycloakSession.getContext().getRealm().getRole(configuration.getLongLivedTokenRole())).map(user::hasRole).orElse(Boolean.valueOf(false)).booleanValue();
        newToken.expiration(tokenConfiguration.computeTokenExpiration(newToken.getExpiration(), longLivedTokenAllowed));
    }

    static class ConfigurableTokenException extends Exception {
        public ConfigurableTokenException(String message) {
            super(message);
        }
    }
}
