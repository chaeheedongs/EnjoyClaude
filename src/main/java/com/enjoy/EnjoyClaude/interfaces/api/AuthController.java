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
    public ResponseEntity<TokenViewResponse> login(@Valid @RequestBody LoginViewRequest request) {
        TokenViewResponse response = authApplicationService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenViewResponse> refresh(@Valid @RequestBody RefreshTokenViewRequest request) {
        TokenViewResponse response = authApplicationService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenViewRequest request) {
        authApplicationService.logout(request.getRefreshToken());
        return ResponseEntity.ok().build();
    }
}
