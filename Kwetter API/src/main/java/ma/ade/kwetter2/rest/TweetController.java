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
        if(tweets.isEmpty())
        {
            return Response.noContent().build();
        }
        return Response.ok(tweets).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response AddTweet(Tweet tweet){
        Tweet createdTweet = tweetService.addTweet(tweet);
        return Response.ok(createdTweet).build();
    }

    @PATCH
    public Response UpdateTweet(Tweet tweet)
    {
        tweetService.updateTweet(tweet);
        return Response.ok().build();
    }

    @DELETE
    public Response DeleteTweet(long tweetID){
        tweetService.removeTweet(tweetID);
        return Response.ok().build();
    }

    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response GetTweet(@PathParam("id") long id) {
        Tweet tweet = tweetService.getTweet(id);
        if(tweet == null)
        {
            return Response.noContent().build();
        }
        return Response.ok(tweet).build();
    }

    @POST
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response SearchTweet(String query) {
        Collection<Tweet> foundTweets = tweetService.searchTweet(query);
        if(foundTweets.isEmpty())
        {
            return Response.noContent().build();
        }
        return Response.ok(foundTweets).build();
    }
}
