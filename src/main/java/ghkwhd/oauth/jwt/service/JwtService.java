package ghkwhd.oauth.jwt.service;

import ghkwhd.oauth.jwt.constants.JwtUtils;
import ghkwhd.oauth.jwt.domain.RefreshToken;
import ghkwhd.oauth.jwt.repository.JwtRepository;
import ghkwhd.oauth.member.domain.Member;
import ghkwhd.oauth.member.repository.MemberRepository;
import ghkwhd.oauth.security.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtRepository jwtRepository;
    private final MemberRepository memberRepository;

    public RefreshToken save(RefreshToken refreshToken) {
        return jwtRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return jwtRepository.findByToken(token);
    }

    public String renewToken(String refreshToken) {
        // token 이 존재하는지 찾고, 존재한다면 RefreshToken 안의 memberId 를 가져와서 member 를 찾은 후 AccessToken 생성
        RefreshToken token = this.findByToken(refreshToken).orElseThrow(NoSuchElementException::new);
        Member member = memberRepository.findById(token.getMemberId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다"));
        return JwtUtils.generateAccessToken(member);
    }
}
