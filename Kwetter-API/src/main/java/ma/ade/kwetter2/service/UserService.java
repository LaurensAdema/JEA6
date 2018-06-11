package ma.ade.kwetter2.service;

import ma.ade.kwetter2.database.interfaces.IUserDbManager;
import ma.ade.kwetter2.domain.User;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Stateless
public class UserService
{
    @Inject
    private IUserDbManager userDbManager;

    public User getUser(long id)
    {
        ma.ade.kwetter2.database.objects.User result = userDbManager.get(id);
        if (result == null)
            return null;
        else
            return result.Convert();
    }

    public User getUserByEmail(String email) {
       ma.ade.kwetter2.database.objects.User result = userDbManager.getByEmail(email);
        if (result == null)
            return null;
        else
            return result.Convert();
    }

    public Collection<User> getUsers()
    {
        return userDbManager.getAll().map(ma.ade.kwetter2.database.objects.User::Convert).collect(Collectors.toList());
    }

    public User addUser(User user)
    {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
        return userDbManager.create(user.Convert()).Convert();
    }

    public void updateUser(User user)
    {
        ma.ade.kwetter2.database.objects.User databaseUser = userDbManager.get(user.getId());
        ma.ade.kwetter2.database.objects.User newDatabaseUser = user.Convert();
        if(user.getPassword() != null && !user.getPassword().isEmpty()){
            newDatabaseUser.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
        } else {
            newDatabaseUser.setPassword(databaseUser.getPassword());
        }
        userDbManager.update(newDatabaseUser);
    }

    public void removeUser(long userID)
    {
        userDbManager.delete(userID);
    }

    public boolean authenticateUser(long userID, String password) {
        return userDbManager.authenticateUser(userID, password);
    }

    public Collection<User> getFollowing(long userID) {
        return userDbManager.getFollowing(userID).map(ma.ade.kwetter2.database.objects.User::Convert).collect(Collectors.toList());
    }

    public boolean isFollowing(String followerEmail, String followingEmail){
        return userDbManager.isFollowing(followerEmail, followingEmail);
    }
}
