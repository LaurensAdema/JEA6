package ma.ade.kwetter2.service;

import ma.ade.kwetter2.database.interfaces.IUserDbManager;
import ma.ade.kwetter2.domain.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

@Stateless
public class UserService
{
    @Inject
    private IUserDbManager userDbManager;

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
                users.add(x.Convert()));
        return users;
    }

    public void addUser(User user)
    {
        userDbManager.create(user.Convert());
    }

    public void updateUser(User user)
    {
        ma.ade.kwetter2.database.objects.User databaseUser = userDbManager.get(user.getId());
        databaseUser.setEmail(user.getEmail());
        databaseUser.setProfile(user.getProfile().Convert());
        userDbManager.update(databaseUser);
    }

    public void removeUser(User user)
    {
        ma.ade.kwetter2.database.objects.User databaseUser = userDbManager.get(user.getId());
        userDbManager.delete(databaseUser);
    }
}
