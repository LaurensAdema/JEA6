package ladema.kwetter.database.managers;

import ladema.kwetter.database.interfaces.IUserDbManager;
import ladema.kwetter.database.objects.User;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class UserDbManager extends BaseDbManager<User> implements IUserDbManager
{
    @PersistenceContext(name="Kwetter")
    private EntityManager em;

    public UserDbManager()
    {
        super(User.class);
    }

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public User getWithEmail(String email)
    {
        return (User) em.createQuery(
                "SELECT u FROM User u WHERE u.email LIKE :email")
                .setParameter("email", email)
                .getResultList().get(0);
    }

    @Override
    public Collection<User> getAll()
    {
        return getEntityManager().createQuery("SELECT U FROM User U").getResultList();
    }
}
