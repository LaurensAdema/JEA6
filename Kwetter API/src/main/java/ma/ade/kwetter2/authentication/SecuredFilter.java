package ma.ade.kwetter2.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import javax.annotation.Priority;
import javax.crypto.KeyGenerator;
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

@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class SecuredFilter implements ContainerRequestFilter{
    public static final String AUTHENTICATION_SCHEME = "Bearer";

    @Inject
    private KeyGenerator keyGenerator;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the HTTP Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        try {

            // Validate the token
            Key key = keyGenerator.generateKey();
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);

            String username = claimsJws.getBody().getSubject();
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

        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}

