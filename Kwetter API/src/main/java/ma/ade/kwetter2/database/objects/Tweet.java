package ma.ade.kwetter2.database.objects;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Tweet implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    
    @ManyToOne
    private User user;

    public Tweet() {
    }

    public Tweet(String message, User user)
    {
        this.message = message;
        this.user = user;
    }
    
    public Tweet(ma.ade.kwetter2.domain.Tweet tweet)
    {
        this.id = tweet.getId();
        this.message = tweet.getMessage();
        this.user = tweet.getUser().Convert();
    }
    
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
    
    public ma.ade.kwetter2.domain.Tweet Convert()
    {
        return new ma.ade.kwetter2.domain.Tweet(this);
    }
}
