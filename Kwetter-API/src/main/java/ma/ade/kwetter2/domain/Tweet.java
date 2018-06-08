package ma.ade.kwetter2.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@XmlRootElement
public class Tweet implements Serializable
{
    private Long id;
    private String message;
    private User user;
    private OffsetDateTime date;
    private Set<Flag> flags;
    private Set<User> likes;

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

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public Set<Flag> getFlags() {
        return flags;
    }

    public void setFlags(Set<Flag> flags) {
        this.flags = flags;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public Tweet() {
    }

    public Tweet(Long id, String message, User user, OffsetDateTime date, Set<Flag> flags, Set<User> likes)
    {
        this.id = id;
        this.message = message;
        this.user = user;
        this.date = date;
        this.flags = flags;
        this.likes = likes;
    }
    
    public Tweet(String message, User user)
    {
        this.message = message;
        this.user = user;
        this.date = OffsetDateTime.now();
        this.flags = new HashSet<>();
        this.likes = new HashSet<>();
    }
    
    public Tweet(ma.ade.kwetter2.database.objects.Tweet tweet)
    {
        this.id = tweet.getId();
        this.message = tweet.getMessage();
        this.user = tweet.getUser().Convert();
        this.date = tweet.getDate();
        this.flags = tweet.getFlags().stream().map(flag -> flag.Convert()).collect(Collectors.toSet());
        this.likes = tweet.getLikes().stream().map(like -> like.Convert()).collect(Collectors.toSet());
    }
    
    public ma.ade.kwetter2.database.objects.Tweet Convert()
    {
        return new ma.ade.kwetter2.database.objects.Tweet(this);
    }
}
