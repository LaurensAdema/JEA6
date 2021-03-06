package ma.ade.kwetter2.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@XmlRootElement
public class User implements Serializable
{
    private Long id;   
    private String email;
    private String password;
    private Profile profile;
    private Set<User> following;
    private Set<User> followers;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Profile getProfile()
    {
        return profile;
    }

    public void setProfile(Profile profile)
    {
        this.profile = profile;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public User() {
    }

    public User(Long id, String email, Profile profile)
    {
        this.id = id;
        this.email = email;
        this.profile = profile;
    }
    
    public User(String email, String password, Profile profile)
    {
        this.email = email;
        this.password = password;
        this.profile = profile;
    }
    
    public User(ma.ade.kwetter2.database.objects.User user)
    {
        this.id = user.getId();
        this.email = user.getEmail();
        this.profile = user.getProfile().Convert();
        this.following = user.getFollowing().stream().map(ma.ade.kwetter2.database.objects.User::Convert).collect(Collectors.toSet());
        this.followers = new HashSet<>();
    }
    
    public ma.ade.kwetter2.database.objects.User convert()
    {
        return new ma.ade.kwetter2.database.objects.User(this);
    }
}
