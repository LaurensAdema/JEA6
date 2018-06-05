package ma.ade.kwetter2.rest;

import ma.ade.kwetter2.domain.User;
import ma.ade.kwetter2.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Stateless
@Path("/user")
public class UserController {
    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetUsers() {
        Collection<User> users = userService.getUsers();
        if(users.isEmpty()){
            return Response.noContent().build();
        }
        return Response.ok(users).build();
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response AddUser(User user){
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
        User createdUser = userService.addUser(user);
        return Response.ok(createdUser).build();
    }

    @PATCH
    public Response UpdateUser(User user)
    {
        if(user.getPassword() != null && !user.getPassword().isEmpty()){
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
        }
        userService.updateUser(user);
        return Response.ok().build();
    }

    @DELETE
    public Response DeleteUser(long userID){
        userService.removeUser(userID);
        return Response.ok().build();
    }

    @GET
    @Path("/{input}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetUser(@PathParam("input") String input) {
        User foundUser;
        try {
            long id = Long.parseLong(input);
            foundUser = userService.getUser(id);
        } catch (Exception e) {
            foundUser = userService.getUserByEmail(input);
        }
        if(foundUser != null){
            return Response.ok(foundUser).build();
        } else {
            return Response.noContent().build();
        }
    }
}
