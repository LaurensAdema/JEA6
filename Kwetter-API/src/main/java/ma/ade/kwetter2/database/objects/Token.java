package ma.ade.kwetter2.database.objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
public class Token implements Serializable {
    @Id
    private String accessToken;
    private String sessionId;

    @ManyToOne
    private User user;

    public Token() {
    }

    public Token(ma.ade.kwetter2.domain.Token token) {
        this.accessToken = token.getAccessToken();
        this.sessionId = token.getSessionId();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ma.ade.kwetter2.domain.Token Convert()
    {
        return new ma.ade.kwetter2.domain.Token(this);
    }
}
