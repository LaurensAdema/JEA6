package ma.ade.kwetter2.rest;

import ma.ade.kwetter2.domain.Tweet;
import ma.ade.kwetter2.service.TweetService;
import ma.ade.kwetter2.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Stateless
@Path("/tweet")
public class TweetController {
    @Inject
    private TweetService tweetService;

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetTweets() {
        Collection<Tweet> tweets = tweetService.getTweets();
        return Response.ok(tweets).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response AddTweet(Tweet tweet){
        tweet.setUser(userService.getUser(1));
        Tweet createdTweet = tweetService.addTweet(tweet);
        return Response.ok(createdTweet).build();
    }

    @PATCH
    public void UpdateTweet(Tweet tweet)
    {
        tweetService.updateTweet(tweet);
    }

    @DELETE
    public void DeleteTweet(long tweetID){
        tweetService.removeTweet(tweetID);
    }

    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetTweet(@PathParam("id") long id) {
        Tweet tweet = tweetService.getTweet(id);
        return Response.ok(tweet).build();
    }

    @POST
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Tweet> SearchTweet(String query) {
        return tweetService.searchTweet(query);
    }
}
