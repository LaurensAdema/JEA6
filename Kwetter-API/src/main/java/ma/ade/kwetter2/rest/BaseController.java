package ma.ade.kwetter2.rest;

import ma.ade.kwetter2.authentication.UseAuthentication;
import ma.ade.kwetter2.domain.User;
import ma.ade.kwetter2.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.net.URI;

@Produces(MediaType.APPLICATION_JSON)
@UseAuthentication
public abstract class BaseController {

    @Context
    private SecurityContext securityContext;

    protected SecurityContext getSecurityContext() {
        return securityContext;
    }

    @Inject
    private UserService userService;

    protected User getUser() {
        return userService.getUserByEmail(securityContext.getUserPrincipal().getName());
    }

    protected Response ok() {
        return Response.ok().build();
    }

    protected Response ok(Object entity) {
        return Response.ok(entity).build();
    }

    protected Response notFound() {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    protected Response created(Object entity, URI location) {
        return Response.created(location).entity(entity).build();
    }

    protected Response forbidden() {
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    protected Response unauthorized() {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}