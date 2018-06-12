package ma.ade.kwetter2.rest;

import ma.ade.kwetter2.authentication.RequireAuthentication;
import ma.ade.kwetter2.domain.Flag;
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
@Path("/tweet")
public class TweetController extends BaseController {
    @Context
    private UriInfo uriInfo;

    @Inject
    private TweetService tweetService;

    @Inject
    private UserService userService;

    @GET
    public Response GetTweets() {
        Collection<Tweet> tweets = tweetService.getTweets();
        if(tweets.isEmpty())
        {
            return Response.noContent().build();
        }
        return ok(tweets);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @RequireAuthentication
    public Response AddTweet(Tweet tweet){
        if(tweet.getMessage().length() > 140) {
            throw new IllegalArgumentException("Illegal number of characters");
        }

        tweet.setUser(getUser());
        Tweet createdTweet = tweetService.addTweet(tweet);

        URI getTweetLink = uriInfo.getBaseUriBuilder().path(TweetController.class).path(TweetController.class, "GetTweet").build(createdTweet.getId());
        return created(createdTweet, getTweetLink);
    }

    @PATCH
    @RequireAuthentication
    public Response UpdateTweet(Tweet tweet)
    {
        tweetService.updateTweet(tweet);
        return ok();
    }

    @DELETE
    @RequireAuthentication
    public Response DeleteTweet(long tweetID){
        tweetService.removeTweet(tweetID);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    public Response GetTweet(@PathParam("id") long id) {
        Tweet tweet = tweetService.getTweet(id);
        if(tweet == null)
        {
            return notFound();
        }
        return ok(tweet);
    }

    @GET
    @Path("/me")
    @RequireAuthentication
    public Response GetTweetsForMe() {
        User user = getUser();
        Collection<Tweet> tweets = tweetService.getTweetsFor(user.getId());

        if(tweets.isEmpty())
        {
            return Response.noContent().build();
        }
        return ok(tweets);
    }

    @POST
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response SearchTweet(String query) {
        Collection<Tweet> foundTweets = tweetService.searchTweet(query);
        if(foundTweets.isEmpty())
        {
            return notFound();
        }
        return ok(foundTweets);
    }

    @POST
    @Path("/{id}/like")
    @RequireAuthentication
    public Response like(@PathParam("id") long id) {
        Tweet tweet = tweetService.toggleLike(id, getUser().getId());

        if (tweet == null) {
            return notFound();
        }

        Link getUserLink = Link.fromUriBuilder(uriInfo.getBaseUriBuilder().path(UserController.class).path(UserController.class, "GetUser")).rel("user").build(tweet.getUser().getEmail());
        return ok(tweet, getUserLink);
    }

    @POST
    @Path("/{id}/flag")
    @RequireAuthentication
    public Response flag(@PathParam("id") long id, Flag flag) {
        flag.setFlagger(getUser());
        Tweet tweet = tweetService.addFlag(id, flag);
        if(tweet == null) {
            return notModified("Already flagged");
        }

        Link getUserLink = Link.fromUriBuilder(uriInfo.getBaseUriBuilder().path(UserController.class).path(UserController.class, "GetUser")).rel("user").build(tweet.getUser().getEmail());
        return ok(tweet, getUserLink);
    }
}
