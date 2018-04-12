package ma.ade.kwetter2.rest;

import ma.ade.kwetter2.domain.User;
import ma.ade.kwetter2.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/user")
public class UserController {
    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> GetUsers() {
        return userService.getUsers();
    }

}
