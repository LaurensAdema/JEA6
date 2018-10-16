package ma.ade.kwetter2.authentication;

import java.security.Key;

public interface IKeyGenerator {
    Key generate();
    Key generate(String keyString);
}