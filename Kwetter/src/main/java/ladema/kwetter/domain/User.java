package ladema.kwetter.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User implements Serializable
{
    private Long id;   
    private String email;
    private String password;
    private Profile profile;

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
    
    public User(ladema.kwetter.database.objects.User user)
    {
        this.id = user.getId();
        this.email = user.getEmail();
        this.profile = user.getProfile().Convert();
    }
    
    public ladema.kwetter.database.objects.User Convert()
    {
        return new ladema.kwetter.database.objects.User(this);
    }
}
