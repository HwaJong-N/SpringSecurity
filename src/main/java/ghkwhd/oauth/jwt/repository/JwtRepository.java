package ghkwhd.oauth.jwt.repository;

import ghkwhd.oauth.jwt.constants.JwtConstants;
import ghkwhd.oauth.jwt.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class JwtRepository {

    private final RedisTemplate redisTemplate;

    public RefreshToken save(RefreshToken refreshToken) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken.getToken(), refreshToken.getMemberId());
        redisTemplate.expire(refreshToken.getToken(), JwtConstants.REFRESH_EXP_TIME, TimeUnit.MILLISECONDS); // 5분 동안 Redis 에 저장
        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String userId = valueOperations.get(refreshToken);

        if (Objects.isNull(userId)) {
            return Optional.empty();
        }
        return Optional.of(new RefreshToken(refreshToken, userId));
    }
}
