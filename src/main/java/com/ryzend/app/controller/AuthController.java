package com.ryzend.app.controller;

import com.ryzend.app.dto.request.CreateTokenRequest;
import com.ryzend.app.dto.response.CreateTokenResponse;
import com.ryzend.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/token")
    public CreateTokenResponse createToken(@RequestBody CreateTokenRequest request) {
        return authService.createToken(request);
    }
}
