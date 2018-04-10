package ladema.kwetter.database.managers;

import ladema.kwetter.database.interfaces.ITweetDbManager;
import ladema.kwetter.database.objects.Tweet;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class TweetDbManager extends BaseDbManager<Tweet> implements ITweetDbManager
{
    @PersistenceContext(name="Kwetter")
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
}
