package dao;

import domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless @JPA
public class UserDaoJPA extends DaoFacade<User> implements UserDao {

    @PersistenceContext
    private EntityManager em;
    
    public UserDaoJPA() {
        super(User.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public User findByName(String name) {
        TypedQuery<User> query = em.createNamedQuery("user.findByname", User.class);
        query.setParameter("name", name);
        List<User> result = query.getResultList();
        System.out.println("count: " + result.size());
        return result.get(0);
    }

    @Override
    public ArrayList<User> getUsers() {
         Query query = em.createQuery("SELECT u FROM User u");
         return  new ArrayList<>(query.getResultList());
    }
    
}
