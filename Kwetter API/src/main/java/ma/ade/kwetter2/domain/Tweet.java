package ma.ade.kwetter2.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
public class Tweet implements Serializable
{
    private Long id;
    private String message;
    private User user;
    private Date date;

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

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public Tweet(Long id, String message, User user, Date date)
    {
        this.id = id;
        this.message = message;
        this.user = user;
        this.date = date;
    }
    
    public Tweet(String message, User user)
    {
        this.message = message;
        this.user = user;
        this.date = new Date();
    }
    
    public Tweet(ma.ade.kwetter2.database.objects.Tweet tweet)
    {
        this.id = tweet.getId();
        this.message = tweet.getMessage();
        this.user = tweet.getUser().Convert();
        this.date = tweet.getDate();
    }
    
    public ma.ade.kwetter2.database.objects.Tweet Convert()
    {
        return new ma.ade.kwetter2.database.objects.Tweet(this);
    }
}
