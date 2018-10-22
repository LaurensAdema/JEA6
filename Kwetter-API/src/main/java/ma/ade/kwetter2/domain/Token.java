package ma.ade.kwetter2.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.OffsetDateTime;

@XmlRootElement
public class Token implements Serializable {
    private String accessToken;
    private String remoteAddress;
    private OffsetDateTime issuedAt;
    private OffsetDateTime expirationDate;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String sessionId) {
        this.remoteAddress = sessionId;
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

    public Token() {
    }

    public Token(ma.ade.kwetter2.database.objects.Token token) {
        this.accessToken = token.getAccessToken();
        this.remoteAddress = token.getRemoteAddress();
        this.issuedAt = token.getIssuedAt();
        this.expirationDate = token.getExpirationDate();
    }

    public Token(String accessToken, String remoteAddress, OffsetDateTime issuedAt, OffsetDateTime expirationDate) {
        this.accessToken = accessToken;
        this.remoteAddress = remoteAddress;
        this.issuedAt = issuedAt;
        this.expirationDate = expirationDate;
    }

    public ma.ade.kwetter2.database.objects.Token convert()
    {
        return new ma.ade.kwetter2.database.objects.Token(this);
    }
}
