package com.ryzend.app.service;

import com.ryzend.app.dto.request.CreateTokenRequest;
import com.ryzend.app.dto.response.CreateTokenResponse;
import com.ryzend.app.exception.InvalidCredentialsException;
import com.ryzend.app.security.jwt.Jwt;
import com.ryzend.app.security.jwt.JwtDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtDecoder jwtDecoder;

    public CreateTokenResponse createToken(CreateTokenRequest request) {
        UserDetails userDetails = authenticate(request.username(), request.password());
        Jwt jwt = jwtDecoder.createToken(userDetails);

        return new CreateTokenResponse(jwt.tokenValue());
    }

    private UserDetails authenticate(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            return (UserDetails) authenticationManager.authenticate(token)
                    .getPrincipal();
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Invalid username or password", ex);
        }
    }
}
