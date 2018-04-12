package ma.ade.kwetter2.database.managers;

import ma.ade.kwetter2.database.interfaces.IUserDbManager;
import ma.ade.kwetter2.database.objects.User;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.Collection;

@Stateless
public class UserDbManager extends BaseDbManager<User> implements IUserDbManager
{
    @PersistenceContext
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

    public Collection<User> getAll()
    {
        return getEntityManager().createQuery("SELECT U FROM User U").getResultList();
    }
}
