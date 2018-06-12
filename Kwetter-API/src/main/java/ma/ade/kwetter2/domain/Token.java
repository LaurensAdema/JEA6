package ma.ade.kwetter2.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Token implements Serializable {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Token() {
    }

    public Token(String accessToken) {
        this.accessToken = accessToken;
    }
}
