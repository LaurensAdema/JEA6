package ma.ade.kwetter2.rest;

import ma.ade.kwetter2.domain.Tweet;
import ma.ade.kwetter2.service.TweetService;
import ma.ade.kwetter2.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    public Collection<Tweet> GetTweets() {
        return tweetService.getTweets();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Tweet AddTweet(Tweet tweet){
        tweet.setUser(userService.getUser(1));
        return tweetService.addTweet(tweet);
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
    public Tweet GetTweet(@PathParam("id") long id) {
        Tweet tweet = tweetService.getTweet(id);
        return tweet;
    }
}
