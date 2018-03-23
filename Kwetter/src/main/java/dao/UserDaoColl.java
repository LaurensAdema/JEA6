package dao;

import domain.User;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.inject.Default;

@Stateless @Default
public class UserDaoColl implements UserDao {

    CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();

    @Override
    public void create(User user) {
        users.add(user);
    }
    
    @Override
    public void remove(User user) {
       users.remove(user);
    }

    @Override
    public ArrayList<User> getUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getName().contentEquals(name)) {
                return user;
            }
        }
        return null;
    }
    
    
    @PostConstruct
    public void init(){
        System.out.println("UserDaoColl");
    }
    
    
    public UserDaoColl() {
    }
    
}
