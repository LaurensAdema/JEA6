package ma.ade.kwetter2.rest;

import ma.ade.kwetter2.authentication.Secured;
import ma.ade.kwetter2.domain.Tweet;
import ma.ade.kwetter2.domain.User;
import ma.ade.kwetter2.service.TweetService;
import ma.ade.kwetter2.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Collection;

@Stateless
@Path("/user")
public class UserController extends BaseController {
    @Context
    private UriInfo uriInfo;

    @Inject
    private UserService userService;

    @Inject
    private TweetService tweetService;

    @GET
    public Response GetUsers() {
        Collection<User> users = userService.getUsers();
        if(users.isEmpty()){
            return Response.noContent().build();
        }
        return ok(users);
    }
    
    @PUT
    @Secured
    public Response AddUser(User user){
        User createdUser = userService.addUser(user);
        URI location = uriInfo.getBaseUriBuilder()
                .path(UserController.class)
                .path(UserController.class, "GetUser")
                .build(createdUser.getId());

        return created(createdUser, location);
    }

    @PATCH
    @Secured
    public Response UpdateUser(User user)
    {
        userService.updateUser(user);
        return ok();
    }

    @DELETE
    @Secured
    public Response DeleteUser(long userID){
        userService.removeUser(userID);
        return ok();
    }

    @GET
    @Path("/{input}")
    public Response GetUser(@PathParam("input") String input) {
        User foundUser;
        try {
            long id = Long.parseLong(input);
            foundUser = userService.getUser(id);
        } catch (Exception e) {
            foundUser = userService.getUserByEmail(input);
        }
        if(foundUser != null){
            return ok(foundUser);
        } else {
            return notFound();
        }
    }

    @GET
    @Path("/me")
    @Secured
    public Response GetMe() {
        User user = getUser();
        Link userLink = Link.fromUriBuilder(uriInfo.getBaseUriBuilder()
                .path(UserController.class)
                .path(UserController.class, "GetUser"))
                .rel("user")
                .build(user.getEmail());

        return Response.ok(user).links(userLink).build();
    }

    @GET
    @Path("/{input}/tweets")
    public Response GetTweetsOf(@PathParam("input") String input) {
        User foundUser;
        try {
            long id = Long.parseLong(input);
            foundUser = userService.getUser(id);
        } catch (Exception e) {
            foundUser = userService.getUserByEmail(input);
        }
        if(foundUser != null){
            Collection<Tweet> foundTweets = tweetService.getTweetsOf(foundUser.getId());
            return ok(foundTweets);
        } else {
            return notFound();
        }
    }
}
