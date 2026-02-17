package com.enjoy.EnjoyClaude.interfaces.api;

import com.enjoy.EnjoyClaude.applications.auth.AuthApplicationService;
import com.enjoy.EnjoyClaude.interfaces.dto.request.LoginViewRequest;
import com.enjoy.EnjoyClaude.interfaces.dto.request.RefreshTokenViewRequest;
import com.enjoy.EnjoyClaude.interfaces.dto.request.SignupViewRequest;
import com.enjoy.EnjoyClaude.interfaces.dto.response.TokenViewResponse;
import com.enjoy.EnjoyClaude.interfaces.dto.response.UserDetailViewResponse;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<UserDetailViewResponse> signup(@Valid @RequestBody SignupViewRequest request) {
        UserDetailViewResponse response = authApplicationService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenViewResponse> login(@Valid @RequestBody LoginViewRequest request, HttpServletRequest httpRequest) {
        TokenViewResponse response = authApplicationService.login(request, httpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenViewResponse> refresh(@Valid @RequestBody RefreshTokenViewRequest request, HttpServletRequest httpRequest) {
        TokenViewResponse response = authApplicationService.refreshToken(request, httpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenViewRequest request, HttpServletRequest httpRequest) {
        // Authorization 헤더에서 Access Token 추출
        String accessToken = extractAccessToken(httpRequest);
        authApplicationService.logout(request.getRefreshToken(), accessToken);
        return ResponseEntity.ok().build();
    }

    private String extractAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
