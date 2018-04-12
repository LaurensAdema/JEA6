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
        return tweetDbManager.get(id).Convert();
    }

    public Collection<Tweet> getTweets()
    {
        Collection<Tweet> tweets = new ArrayList<>();
        tweetDbManager.getAll().forEach(x ->
                tweets.add(x.Convert()));
        return tweets;
    }

    public void addTweet(Tweet tweet)
    {
        tweetDbManager.create(tweet.Convert());
    }

    public void updateTweet(Tweet tweet)
    {
        ma.ade.kwetter2.database.objects.Tweet databaseTweet = tweetDbManager.get(tweet.getId());
        databaseTweet.setMessage(tweet.getMessage());
        tweetDbManager.update(databaseTweet);
    }

    public void removeTweet(Tweet tweet)
    {
        ma.ade.kwetter2.database.objects.Tweet databaseTweet = tweetDbManager.get(tweet.getId());
        tweetDbManager.delete(databaseTweet);
    }
}
