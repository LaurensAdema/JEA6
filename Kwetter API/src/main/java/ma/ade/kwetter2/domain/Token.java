package ma.ade.kwetter2.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Token {
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
