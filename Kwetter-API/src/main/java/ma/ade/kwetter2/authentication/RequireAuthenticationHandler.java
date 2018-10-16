package ma.ade.kwetter2.authentication;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@RequireAuthentication
@Provider
@Priority(Priorities.AUTHENTICATION)
public class RequireAuthenticationHandler extends BaseAuthenticationHandler implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String accessToken = getAccessToken(requestContext);
        if(accessToken == null || accessToken.isEmpty()){
            unauthorized(requestContext);
            return;
        }
        try {
            String username = validateToken(accessToken, servletRequest.getSession().getId());
            setSecurityContext(requestContext, username);
        } catch (Exception e) {
            forbidden(requestContext);
        }
    }
}

