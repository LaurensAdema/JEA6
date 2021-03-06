package ma.ade.kwetter2.database.objects;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
public class Flag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OffsetDateTime date;
    private String reason;

    @ManyToOne
    private User flagger;
    @ManyToOne
    private Tweet tweet;

    public Flag(OffsetDateTime date, String reason, User flagger, Tweet tweet) {
        this.date = date;
        this.reason = reason;
        this.flagger = flagger;
        this.tweet = tweet;
    }

    public Flag(ma.ade.kwetter2.domain.Flag flag){
        this.id = flag.getId();
        this.date = flag.getDate();
        this.reason = flag.getReason();
        this.flagger = flag.getFlagger().convert();
    }

    public Flag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getFlagger() {
        return flagger;
    }

    public void setFlagger(User flagger) {
        this.flagger = flagger;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public ma.ade.kwetter2.domain.Flag Convert()
    {
        return new ma.ade.kwetter2.domain.Flag(this);
    }
}
