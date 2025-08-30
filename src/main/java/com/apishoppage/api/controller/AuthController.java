package com.apishoppage.api.controller;

import com.apishoppage.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(Authentication authentication){
        return ResponseEntity.ok(authService.getJwtTokensAfterAuthentication(authentication));
    }

}
