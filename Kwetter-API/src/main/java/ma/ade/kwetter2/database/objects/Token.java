package ma.ade.kwetter2.database.objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@XmlRootElement
public class Token implements Serializable {
    @Id
    private String accessToken;
    private String remoteAddress;
    private OffsetDateTime issuedAt;
    private OffsetDateTime expirationDate;

    @ManyToOne
    private User user;

    public Token() {
    }

    public Token(ma.ade.kwetter2.domain.Token token) {
        this.accessToken = token.getAccessToken();
        this.remoteAddress = token.getRemoteAddress();
        this.issuedAt = token.getIssuedAt();
        this.expirationDate = token.getExpirationDate();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteaddress) {
        this.remoteAddress = remoteAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OffsetDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(OffsetDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public OffsetDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(OffsetDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public ma.ade.kwetter2.domain.Token Convert()
    {
        return new ma.ade.kwetter2.domain.Token(this);
    }
}
