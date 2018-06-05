package ma.ade.kwetter2.rest;

import ma.ade.kwetter2.authentication.Secured;
import ma.ade.kwetter2.domain.Tweet;
import ma.ade.kwetter2.service.TweetService;
import ma.ade.kwetter2.service.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
    @Secured
    public Response AddTweet(Tweet tweet){
        tweet.setUser(getUser());
        Tweet createdTweet = tweetService.addTweet(tweet);
        URI location = uriInfo.getBaseUriBuilder()
                .path(TweetController.class)
                .path(TweetController.class, "GetTweet")
                .build(createdTweet.getId());

        return created(createdTweet, location);
    }

    @PATCH
    @Secured
    public Response UpdateTweet(Tweet tweet)
    {
        tweetService.updateTweet(tweet);
        return ok();
    }

    @DELETE
    @Secured
    public Response DeleteTweet(long tweetID){
        tweetService.removeTweet(tweetID);
        return Response.ok().build();
    }

    @Path("/{id}")
    public Response GetTweet(@PathParam("id") long id) {
        Tweet tweet = tweetService.getTweet(id);
        if(tweet == null)
        {
            return notFound();
        }
        return ok(tweet);
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
}
