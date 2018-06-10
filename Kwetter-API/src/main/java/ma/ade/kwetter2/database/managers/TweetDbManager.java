package ma.ade.kwetter2.database.managers;

import ma.ade.kwetter2.database.interfaces.ITweetDbManager;
import ma.ade.kwetter2.database.objects.Tweet;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;
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


}
