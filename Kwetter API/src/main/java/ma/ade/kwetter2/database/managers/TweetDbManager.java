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

    public Collection<Tweet> getAll()
    {
        return getEntityManager().createQuery("SELECT T FROM Tweet T").getResultList();
    }

    public Collection<Tweet> search(String query) {
        List results = em.createQuery("SELECT t FROM Tweet t WHERE lower(t.message) LIKE concat('%', lower(:query), '%') ").setParameter("query", query).getResultList();
        return results;
    }
}
