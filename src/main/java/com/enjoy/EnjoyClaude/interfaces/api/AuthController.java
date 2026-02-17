package com.enjoy.EnjoyClaude.interfaces.api;

import com.enjoy.EnjoyClaude.applications.auth.AuthApplicationService;
import com.enjoy.EnjoyClaude.domains.code.TokenType;
import com.enjoy.EnjoyClaude.interfaces.dto.request.LoginViewRequest;
import com.enjoy.EnjoyClaude.interfaces.dto.request.RefreshTokenViewRequest;
import com.enjoy.EnjoyClaude.interfaces.dto.request.SignupViewRequest;
import com.enjoy.EnjoyClaude.interfaces.dto.response.TokenViewResponse;
import com.enjoy.EnjoyClaude.interfaces.dto.response.UserDetailViewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthApplicationService authApplicationService;

    @PostMapping("/signup")
    public ResponseEntity<UserDetailViewResponse> signup(@Valid @RequestBody final SignupViewRequest request) {
        final UserDetailViewResponse response = authApplicationService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenViewResponse> login(@Valid @RequestBody final LoginViewRequest request, final HttpServletRequest httpRequest) {
        final TokenViewResponse response = authApplicationService.login(request, httpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenViewResponse> refresh(@Valid @RequestBody final RefreshTokenViewRequest request, final HttpServletRequest httpRequest) {
        final TokenViewResponse response = authApplicationService.refreshToken(request, httpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody final RefreshTokenViewRequest request, final HttpServletRequest httpRequest) {
        // Authorization 헤더에서 Access Token 추출
        final String accessToken = extractAccessToken(httpRequest);
        authApplicationService.logout(request.getRefreshToken(), accessToken);
        return ResponseEntity.ok().build();
    }

    private String extractAccessToken(final HttpServletRequest request) {
        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return TokenType.BEARER.extractToken(bearerToken);
    }
}
