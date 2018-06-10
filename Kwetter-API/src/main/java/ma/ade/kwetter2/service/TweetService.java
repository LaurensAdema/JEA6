package ma.ade.kwetter2.service;

import ma.ade.kwetter2.database.interfaces.ITweetDbManager;
import ma.ade.kwetter2.domain.Tweet;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Stateless
public class TweetService
{
    @Inject
    private ITweetDbManager tweetDbManager;

    @Inject
    private Event<Tweet> updateTweetEvent;

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
        return tweetDbManager.getAll().map(ma.ade.kwetter2.database.objects.Tweet::Convert).collect(Collectors.toList());
    }

    public Tweet addTweet(Tweet tweet)
    {
        Tweet toAdd = tweetDbManager.create(tweet.Convert()).Convert();
        updateTweetEvent.fireAsync(toAdd);
        return toAdd;
    }

    public void updateTweet(Tweet tweet)
    {
        ma.ade.kwetter2.database.objects.Tweet databaseTweet = tweetDbManager.get(tweet.getId());
        databaseTweet.setMessage(tweet.getMessage());
        tweetDbManager.update(databaseTweet);
        updateTweetEvent.fireAsync(databaseTweet.Convert());
    }

    public void removeTweet(long tweetID)
    {
        tweetDbManager.delete(tweetID);
        Tweet toDelete = new Tweet();
        toDelete.setId(tweetID);
        updateTweetEvent.fireAsync(toDelete);
    }

    public Collection<Tweet> searchTweet(String query) {
        return tweetDbManager.search(query).map(ma.ade.kwetter2.database.objects.Tweet::Convert).collect(Collectors.toList());
    }

    public Collection<Tweet> getTweetsOf(long id) {
        return tweetDbManager.getTweetsOf(id).map(ma.ade.kwetter2.database.objects.Tweet::Convert).collect(Collectors.toList());
    }

    public Collection<Tweet> getTweetsFor(long id) {
        //TODO: Change to following only
        return tweetDbManager.getAll().map(ma.ade.kwetter2.database.objects.Tweet::Convert).collect(Collectors.toList());
    }
}
