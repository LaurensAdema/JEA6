package ma.ade.kwetter2.authentication;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.security.Principal;

@WebFilter("/events")
public class WebSocketAuthenticationHandler extends BaseAuthenticationHandler implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String accessToken = servletRequest.getParameter("access_token");

        if(accessToken != null && !accessToken.isEmpty()){
            String username = validateToken(accessToken, this.servletRequest.getSession().getId());
            servletRequest = new HttpServletRequestWrapper((HttpServletRequest) servletRequest) {
                @Override
                public Principal getUserPrincipal() {
                    return () -> username;
                }
            };
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
