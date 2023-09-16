package ghkwhd.oauth.security.exception;

import org.springframework.security.core.AuthenticationException;

public class PasswordNotMatchException extends AuthenticationException {
    public PasswordNotMatchException(String m) {
        super(m);
    }
}
