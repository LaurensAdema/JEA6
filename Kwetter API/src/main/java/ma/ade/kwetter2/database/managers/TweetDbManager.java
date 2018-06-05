package ma.ade.kwetter2.database.managers;

import ma.ade.kwetter2.database.interfaces.ITweetDbManager;
import ma.ade.kwetter2.database.objects.Tweet;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;

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
    public Collection<Tweet> getAll()
    {
        return getEntityManager().createQuery("SELECT T FROM Tweet T").getResultList();
    }

    @Override
    public Collection<Tweet> search(String query) {
        TypedQuery<Tweet> typedQuery = em.createNamedQuery("tweet.search", Tweet.class);
        typedQuery.setParameter("query", query);
        return typedQuery.getResultList();
    }

    @Override
    public Collection<Tweet> getTweetsOf(long id) {
        TypedQuery<Tweet> query = em.createNamedQuery("tweet.getTweetsOf", Tweet.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
