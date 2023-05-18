package com.keycloak.demoauth;

import com.keycloak.auth.configuration.GreaterBean;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.common.util.RandomString;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.sessions.AuthenticationSessionModel;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;

public class DemoAuthenticator implements Authenticator {

    private static final String TPL_CODE = "login-sms.ftl";

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        UserModel user = context.getUser();
        greet(user.getUsername());
        KeycloakSession session = context.getSession();
        int ttl = 300;
        String code = RandomString.randomCode(6);
        AuthenticationSessionModel authSession = context.getAuthenticationSession();
        authSession.setAuthNote("code", code);
        authSession.setAuthNote("ttl", Long.toString(System.currentTimeMillis() + (ttl * 1000)));

        try {
            context.challenge(context.form().setAttribute("realm", context.getRealm()).createForm(TPL_CODE));
        } catch (Exception e) {
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR,
                    context.form().setError("smsAuthSmsNotSent", e.getMessage())
                            .createErrorPage(Response.Status.INTERNAL_SERVER_ERROR));
        }
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        String enteredCode = context.getHttpRequest().getDecodedFormParameters().getFirst("code");

        AuthenticationSessionModel authSession = context.getAuthenticationSession();
        String code = authSession.getAuthNote("code");
        String ttl = authSession.getAuthNote("ttl");

        if (code == null || ttl == null) {
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR,
                    context.form().createErrorPage(Response.Status.INTERNAL_SERVER_ERROR));
            return;
        }

        boolean isValid = enteredCode.equals(code);
        if (isValid) {
            if (Long.parseLong(ttl) < System.currentTimeMillis()) {
                // expired
                context.failureChallenge(AuthenticationFlowError.EXPIRED_CODE,
                        context.form().setError("smsAuthCodeExpired").createErrorPage(Response.Status.BAD_REQUEST));
            } else {
                // valid
                context.success();
            }
        } else {
            // invalid
            AuthenticationExecutionModel execution = context.getExecution();
            if (execution.isRequired()) {
                context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS,
                        context.form().setAttribute("realm", context.getRealm())
                                .setError("smsAuthCodeInvalid").createForm(TPL_CODE));
            } else if (execution.isConditional() || execution.isAlternative()) {
                context.attempted();
            }
        }
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    @Override
    public void close() {

    }

    private void greet(String username) {
        ServletContext servletContext = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getServletContext();
        WebApplicationContext appCtxt = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        GreaterBean greeter = appCtxt.getBean(GreaterBean.class);
        greeter.greet(username);
    }
}
