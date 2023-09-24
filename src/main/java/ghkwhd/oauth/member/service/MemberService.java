package ghkwhd.oauth.member.service;

import ghkwhd.oauth.member.domain.Member;
import ghkwhd.oauth.member.domain.MemberDTO;
import ghkwhd.oauth.member.domain.SocialType;
import ghkwhd.oauth.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member save(MemberDTO dto) {
        Member user = Member.createUser(dto, passwordEncoder);
        return memberRepository.save(user);
    }

    public Optional<Member> findById(String id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId) {
        return memberRepository.findBySocialTypeAndSocialId(socialType, socialId);
    }

}
