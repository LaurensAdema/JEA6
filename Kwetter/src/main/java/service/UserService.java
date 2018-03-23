/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.JPA;
import dao.UserDao;
import domain.User;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserService {
  
    @Inject @JPA
    private UserDao userDao;

     public void addUser(User user) {
         userDao.create(user);
    }
    
    public void removeUser(User user) {
       userDao.remove(user);
    }
         
    public ArrayList<User> getUseren() {
        return userDao.getUsers();
    }

    public User findByName(String name) {
        return userDao.findByName(name);
    }
    
    public UserService() {
    }  
}
