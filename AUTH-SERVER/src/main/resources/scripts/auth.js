AuthenticationFlowError = Java.type("org.keycloak.authentication.AuthenticationFlowError");

function authenticate(context) {
    LOG.info(script.name + " --> trace auth for: " + user.username);
    context.success();
}