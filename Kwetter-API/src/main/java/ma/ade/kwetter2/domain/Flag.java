package ma.ade.kwetter2.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.OffsetDateTime;

@XmlRootElement
public class Flag implements Serializable {
    private Long id;
    private OffsetDateTime date;
    private String reason;
    private User flagger;

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

    public Flag() {
    }

    public Flag(Long id, OffsetDateTime date, String reason, User flagger) {
        this.id = id;
        this.date = date;
        this.reason = reason;
        this.flagger = flagger;
    }

    public Flag(ma.ade.kwetter2.database.objects.Flag flag){
        this.id = flag.getId();
        this.date = flag.getDate();
        this.reason = flag.getReason();
        this.flagger = flag.getFlagger().Convert();
    }

    public ma.ade.kwetter2.database.objects.Flag convert() {
        return new ma.ade.kwetter2.database.objects.Flag(this);
    }
}
