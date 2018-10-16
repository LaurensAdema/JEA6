package ma.ade.kwetter2.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Token implements Serializable {
    private String accessToken;
    private String sessionId;

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

    public Token() {
    }

    public Token(ma.ade.kwetter2.database.objects.Token token) {
        this.accessToken = token.getAccessToken();
        this.sessionId = token.getSessionId();
    }

    public Token(String accessToken, String sessionId) {
        this.accessToken = accessToken;
        this.sessionId = sessionId;
    }

    public ma.ade.kwetter2.database.objects.Token convert()
    {
        return new ma.ade.kwetter2.database.objects.Token(this);
    }
}
