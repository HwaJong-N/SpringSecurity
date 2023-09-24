package ghkwhd.oauth.member.repository;

import ghkwhd.oauth.member.domain.Member;
import ghkwhd.oauth.member.domain.SocialType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    public MemberRepository memberRepository;

    @Test
    void findSocial() {
        Member member = Member.builder().id("test").name("name").email("email@email.com").socialType(SocialType.KAKAO).socialId("123123123").build();
        memberRepository.save(member);

        Member findMember = memberRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, "123123123").get();
        Assertions.assertThat(member).isEqualTo(findMember);
    }

}