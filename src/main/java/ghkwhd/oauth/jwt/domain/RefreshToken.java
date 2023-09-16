package ghkwhd.oauth.jwt.domain;

import lombok.Data;

@Data
public class RefreshToken {
    private String token;
    private String memberId;

    public RefreshToken(String token, String memberId) {
        this.token = token;
        this.memberId = memberId;
    }
}
