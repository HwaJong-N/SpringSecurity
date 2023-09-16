package ghkwhd.oauth.security.exception;

import org.springframework.security.core.AuthenticationException;

public class InputNotFoundException extends AuthenticationException {
    public InputNotFoundException(String m) {
        super(m);
    }
}
