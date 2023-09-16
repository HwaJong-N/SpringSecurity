package ghkwhd.oauth.security.exception;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
    public UserNotFoundException(String m) {
        super(m);
    }
}
