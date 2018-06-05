package ma.ade.kwetter2.database.managers;

import ma.ade.kwetter2.database.interfaces.IUserDbManager;
import ma.ade.kwetter2.database.objects.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;

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

    public User getByEmail(String email) {
        List results = em.createQuery("SELECT u FROM User u WHERE u.email LIKE :email").setParameter("email", email).getResultList();
        if(results.isEmpty())
            return null;
        else
            return (User) results.get(0);
    }

    @Override
    public boolean authenticateUser(long userID, String password) {
        return BCrypt.checkpw(password, getEntityManager().find(User.class, userID).getPassword());
    }

    public Collection<User> getAll()
    {
        return getEntityManager().createQuery("SELECT U FROM User U").getResultList();
    }
}
