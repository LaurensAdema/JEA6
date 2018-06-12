package ma.ade.kwetter2.rest;

import ma.ade.kwetter2.authentication.UseAuthentication;
import ma.ade.kwetter2.domain.User;
import ma.ade.kwetter2.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
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

    protected Response ok(Link... links) {
        return Response.ok().links(links).build();
    }

    protected Response ok(Object entity, Link... links) {
        return Response.ok(entity).links(links).build();
    }

    protected Response notFound(Link... links) {
        return Response.status(Response.Status.NOT_FOUND).links(links).build();
    }

    protected Response created(Object entity, URI location, Link... links) {
        return Response.created(location).entity(entity).links(links).build();
    }

    protected Response forbidden(Link... links) {
        return Response.status(Response.Status.FORBIDDEN).links(links).build();
    }

    protected Response unauthorized(Link... links) {
        return Response.status(Response.Status.UNAUTHORIZED).links(links).build();
    }

    protected Response notModified(String tag, Link... links) { return Response.notModified(tag).links(links).build(); }
}