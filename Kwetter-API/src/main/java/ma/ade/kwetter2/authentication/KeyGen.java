package ma.ade.kwetter2.authentication;

import java.security.Key;

public interface KeyGen {
    Key generate();
    Key generate(String keyString);
}