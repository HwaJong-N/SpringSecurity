package ghkwhd.oauth.jwt.controller;

import ghkwhd.oauth.jwt.constants.JwtConstants;
import ghkwhd.oauth.jwt.constants.JwtUtils;
import ghkwhd.oauth.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class JwtController {

    private final JwtService jwtService;

    @GetMapping("/renew")
    public ResponseEntity<?> renewToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refreshToken = JwtUtils.getTokenFromHeader(request.getHeader(JwtConstants.JWT_HEADER));
            return ResponseEntity.ok(JwtConstants.JWT_TYPE + jwtService.renewToken(refreshToken));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("Refresh Token 이 만료되었습니다");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
