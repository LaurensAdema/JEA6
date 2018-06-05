package ma.ade.kwetter2.service;

import ma.ade.kwetter2.database.interfaces.ITweetDbManager;
import ma.ade.kwetter2.database.managers.TweetDbManager;
import ma.ade.kwetter2.domain.Tweet;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

@Stateless
public class TweetService
{
    @Inject
    private ITweetDbManager tweetDbManager;

    public Tweet getTweet(long id)
    {
        ma.ade.kwetter2.database.objects.Tweet result = tweetDbManager.get(id);
        if (result == null)
            return null;
        else
            return result.Convert();
    }

    public Collection<Tweet> getTweets()
    {
        Collection<Tweet> tweets = new ArrayList<>();
        tweetDbManager.getAll().forEach(x ->
                tweets.add(x.Convert()));
        return tweets;
    }

    public Tweet addTweet(Tweet tweet)
    {
        return tweetDbManager.create(tweet.Convert()).Convert();
    }

    public void updateTweet(Tweet tweet)
    {
        ma.ade.kwetter2.database.objects.Tweet databaseTweet = tweetDbManager.get(tweet.getId());
        databaseTweet.setMessage(tweet.getMessage());
        tweetDbManager.update(databaseTweet);
    }

    public void removeTweet(long tweetID)
    {
        tweetDbManager.delete(tweetID);
    }

    public Collection<Tweet> searchTweet(String query) {
        Collection<Tweet> tweets = new ArrayList<>();
        tweetDbManager.search(query).forEach(x ->
                tweets.add(x.Convert()));
        return tweets;
    }

    public Collection<Tweet> getTweetsOf(long id) {
        Collection<Tweet> tweets = new ArrayList<>();
        tweetDbManager.getTweetsOf(id).forEach(x ->
                tweets.add(x.Convert()));
        return tweets;
    }
}
