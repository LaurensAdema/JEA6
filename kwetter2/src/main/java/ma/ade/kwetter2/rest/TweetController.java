package ma.ade.kwetter2.rest;

import ma.ade.kwetter2.domain.Tweet;
import ma.ade.kwetter2.service.TweetService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/tweet")
public class TweetController {
    @Inject
    private TweetService tweetService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Tweet> GetTweets() {
        return tweetService.getTweets();
    }
}
