package ma.ade.kwetter2.database.managers;

import ma.ade.kwetter2.database.interfaces.ITweetDbManager;
import ma.ade.kwetter2.database.objects.Flag;
import ma.ade.kwetter2.database.objects.Tweet;
import ma.ade.kwetter2.database.objects.User;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.Optional;
import java.util.stream.Stream;

@Stateless
public class TweetDbManager extends BaseDbManager<Tweet> implements ITweetDbManager
{
    @PersistenceContext
    private EntityManager em;

    public TweetDbManager()
    {
        super(Tweet.class);
    }

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    @Override
    public Stream<Tweet> getAll()
    {
        TypedQuery<Tweet> query = em.createNamedQuery("tweet.getAll", Tweet.class);
        return query.getResultStream();
    }

    @Override
    public Stream<Tweet> search(String query) {
        TypedQuery<Tweet> typedQuery = em.createNamedQuery("tweet.search", Tweet.class);
        typedQuery.setParameter("query", query);
        return typedQuery.getResultStream();
    }

    @Override
    public Stream<Tweet> getTweetsOf(long id) {
        TypedQuery<Tweet> query = em.createNamedQuery("tweet.getTweetsOf", Tweet.class);
        query.setParameter("id", id);
        return query.getResultStream();
    }

    public Tweet toggleLike(long tweetId, long likerId) {
        Tweet tweet = em.find(Tweet.class, tweetId);
        User liker = em.find(User.class, likerId);
        Optional<User> oldLiker = tweet.getLikes().stream().filter(user -> user.getId() == liker.getId()).findFirst();
        if(!oldLiker.isPresent()) {
            tweet.addLike(liker);
        } else {
            tweet.removeLike(oldLiker.get());
        }
        em.merge(tweet);
        return tweet;
    }

    public Tweet addFlag(long tweetId, Flag flag) {
        Tweet tweet = em.find(Tweet.class, tweetId);
        if(tweet.getFlags().stream().noneMatch(existingFlag -> existingFlag.getFlagger().getId() == flag.getFlagger().getId())) {
            flag.setTweet(tweet);
            em.persist(flag);
            tweet.addFlag(flag);
            em.merge(tweet);
            return tweet;
        }
        return null;
    }
}
