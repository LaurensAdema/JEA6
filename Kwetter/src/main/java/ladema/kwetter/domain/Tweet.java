package ladema.kwetter.domain;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tweet implements Serializable
{
    private Long id;
    private String message;
    private User user;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Tweet(Long id, String message, User user)
    {
        this.id = id;
        this.message = message;
        this.user = user;
    }
    
    public Tweet(String message, User user)
    {
        this.message = message;
        this.user = user;
    }
    
    public Tweet(ladema.kwetter.database.objects.Tweet tweet)
    {
        this.id = tweet.getId();
        this.message = tweet.getMessage();
        this.user = tweet.getUser().Convert();
    }
    
    public ladema.kwetter.database.objects.Tweet Convert()
    {
        return new ladema.kwetter.database.objects.Tweet(this);
    }
}
