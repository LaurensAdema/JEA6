package ladema.kwetter.service;

import ladema.kwetter.database.managers.TweetDbManager;
import ladema.kwetter.domain.Tweet;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TweetService
{
    @Inject
    private TweetDbManager tweetDbManager;

    public TweetService()
    {
    }

    public Tweet getTweet(long id)
    {
        return tweetDbManager.get(id).Convert();
    }

    public Collection<Tweet> getTweets()
    {
        Collection<Tweet> tweets = new ArrayList<>();
        tweetDbManager.getAll().forEach(x ->
        {
            tweets.add(x.Convert());
        });
        return tweets;
    }

    public void addTweet(Tweet tweet)
    {
        tweetDbManager.create(tweet.Convert());
    }

    public void updateTweet(Tweet tweet)
    {
        ladema.kwetter.database.objects.Tweet databaseTweet = tweetDbManager.get(tweet.getId());
        databaseTweet.setMessage(tweet.getMessage());
        tweetDbManager.update(databaseTweet);
    }

    public void removeTweet(Tweet tweet)
    {
        ladema.kwetter.database.objects.Tweet databaseTweet = tweetDbManager.get(tweet.getId());
        tweetDbManager.delete(databaseTweet);
    }
}
