package ma.ade.kwetter2.rest;

import ma.ade.kwetter2.domain.User;
import ma.ade.kwetter2.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Stateless
@Path("/user")
public class UserController {
    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> GetUsers() {
        return userService.getUsers();
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public User AddUser(User user){
        return userService.addUser(user);
    }

    @PATCH
    public void UpdateUser(User user)
    {
        userService.updateUser(user);
    }

    @DELETE
    public void DeleteUser(long userID){
        userService.removeUser(userID);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User GetUser(@PathParam("id") long id) {
        User user = userService.getUser(id);
        return user;
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public User GetUserByEmail(@PathParam("email") String email) {
        User user = userService.getUserByEmail(email);
        return user;
    }
}
