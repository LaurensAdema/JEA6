package ma.ade.kwetter2.authentication;

import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateless;
import java.security.Key;

@Stateless
public class KeyGenerator implements IKeyGenerator {
    // RandomStringUtils.randomAlphanumeric(30)
    private final String key = "TempUselessKey";

    @Override
    public Key generate(String keyString) {
        return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
    }

    @Override
    public Key generate() {
        return new SecretKeySpec(key.getBytes(), 0, key.getBytes().length, "DES");
    }
}