package service;

import database.JPA;
import database.managers.UserDbManager;
import domain.User;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserService
{

    @Inject
    @JPA
    private UserDbManager userDbManager;

    public UserService()
    {
    }

    public User getUser(long id)
    {
        return userDbManager.get(id).Convert();
    }

    public User getUserWithEmail(String email)
    {
        return userDbManager.getWithEmail(email).Convert();
    }

    public Collection<User> getUsers()
    {
        Collection<User> users = new ArrayList<>();
        userDbManager.getAll().forEach(x ->
        {
            users.add(x.Convert());
        });
        return users;
    }

    public void addUser(User user)
    {
        userDbManager.create(user.Convert());
    }

    public void updateUser(User user)
    {
        database.objects.User databaseUser = userDbManager.get(user.getId());
        databaseUser.setEmail(user.getEmail());
        databaseUser.setProfile(user.getProfile().Convert());
        userDbManager.update(databaseUser);
    }

    public void removeUser(User user)
    {
        database.objects.User databaseUser = userDbManager.get(user.getId());
        userDbManager.delete(databaseUser);
    }
}
