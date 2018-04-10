package ladema.kwetter.rest;

import ladema.kwetter.domain.Tweet;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import ladema.kwetter.service.TweetService;

@Path("/tweet")
@Stateless
public class TweetController {
    @Inject
    private TweetService tweetService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Tweet> GetTweets() {
        return tweetService.getTweets();
    }
}
