package ma.ade.kwetter2.database.objects;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name="tweet.getTweetsOf", query="SELECT T FROM Tweet T WHERE T.user.id = :id"),
        @NamedQuery(name="tweet.search", query="SELECT t FROM Tweet t WHERE lower(t.message) LIKE concat('%', lower(:query), '%') ")
})
public class Tweet implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private OffsetDateTime date;
    
    @ManyToOne
    private User user;

    public Tweet() {
    }

    public Tweet(Long id, String message, User user, OffsetDateTime date)
    {
        this.id = id;
        this.message = message;
        this.user = user;
        this.date = date;
    }

    public Tweet(String message, User user, OffsetDateTime date)
    {
        this.message = message;
        this.user = user;
        this.date = date;
    }
    
    public Tweet(ma.ade.kwetter2.domain.Tweet tweet)
    {
        this.id = tweet.getId();
        this.message = tweet.getMessage();
        this.user = tweet.getUser().Convert();
        this.date = tweet.getDate();
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

    public OffsetDateTime getDate() { return date; }

    public void setDate(OffsetDateTime date) { this.date = date; }

    public ma.ade.kwetter2.domain.Tweet Convert()
    {
        return new ma.ade.kwetter2.domain.Tweet(this);
    }
}
