package dao;

import domain.User;
import java.util.ArrayList;

public interface UserDao {

    void create(User user);
    
    void remove(User user);

    User findByName(String name);

    ArrayList<User> getUsers();
    
}
