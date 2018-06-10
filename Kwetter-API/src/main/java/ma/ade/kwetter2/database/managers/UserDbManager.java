package ma.ade.kwetter2.database.managers;

import ma.ade.kwetter2.database.interfaces.IUserDbManager;
import ma.ade.kwetter2.database.objects.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.stream.Stream;

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
        TypedQuery<User> query = em.createNamedQuery("user.getByEmail", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    @Override
    public boolean authenticateUser(long userID, String password) {
        return BCrypt.checkpw(password, em.find(User.class, userID).getPassword());
    }

    @Override
    public Stream<User> getAll()
    {
        TypedQuery<User> query = em.createNamedQuery("user.getAll", User.class);
        return query.getResultStream();
    }

    @Override
    public Stream<User> getFollowing(long userID){
        TypedQuery<User> query = em.createNamedQuery("user.getFollowing", User.class);
        //TODO: Create following
        //query.setParameter("userID", userID);
        return query.getResultStream();
    }

    public boolean isFollowing(String followerEmail, String followingEmail){
        //TODO: Create following
        return true;
    }
}
