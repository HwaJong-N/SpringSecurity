package ghkwhd.oauth.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberRole {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    GUEST("ROLE_GUEST");

    private String value;

}
