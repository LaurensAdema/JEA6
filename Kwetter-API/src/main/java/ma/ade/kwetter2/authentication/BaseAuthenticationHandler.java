package ma.ade.kwetter2.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.security.Principal;

public abstract class BaseAuthenticationHandler {
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Inject
    private IKeyGenerator keyGenerator;

    String getAccessToken(ContainerRequestContext requestContext) {
        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String accessToken;

        // If no authorization header is sent, check the URI query parameter according to the Bearer spec
        // http://self-issued.info/docs/draft-ietf-oauth-v2-bearer.html#query-param
        if(authorizationHeader == null || authorizationHeader.isEmpty()){
            // Extract the token from the query parameter
            accessToken = requestContext.getUriInfo().getQueryParameters().getFirst("access_token");
        } else {
            // Extract the token from the HTTP Authorization header
            accessToken = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
        }

        return accessToken;
    }

    String getUsername(String accessToken){
        Key key = keyGenerator.generate();
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(accessToken);
        String username = claimsJws.getBody().getSubject();

        return username;
    }

    void setSecurityContext(ContainerRequestContext requestContext, String username){
        SecurityContext currentSecurityContext = requestContext.getSecurityContext();
        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return () -> username;
            }

            @Override
            public boolean isUserInRole(String s) {
                return false;
            }

            @Override
            public boolean isSecure() {
                return currentSecurityContext.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return AUTHENTICATION_SCHEME;
            }
        });
    }

    void unauthorized(ContainerRequestContext requestContext) {
        abortWith(requestContext, Response.Status.UNAUTHORIZED);
    }

    void forbidden(ContainerRequestContext requestContext){
        abortWith(requestContext, Response.Status.FORBIDDEN);
    }

    private void abortWith(ContainerRequestContext requestContext, Response.Status status){
        requestContext.abortWith(Response.status(status).build());
    }
}

