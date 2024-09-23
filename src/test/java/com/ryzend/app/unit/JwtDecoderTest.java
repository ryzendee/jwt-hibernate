package com.ryzend.app.unit;

import com.ryzend.app.security.jwt.Jwt;
import com.ryzend.app.security.jwt.JwtDecoder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class JwtDecoderTest {

    private JwtDecoder jwtDecoder;
    private Key key;

    @BeforeEach
    void setUp() {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String encodedSecret = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        long expirationTimeMs = 600_000;
        jwtDecoder = new JwtDecoder(encodedSecret, expirationTimeMs);
    }

    @Test
    void createToken_shouldCreateToken() {
        UserDetails userDetails = mock(UserDetails.class);
        String username = "test";

        when(userDetails.getUsername()).thenReturn(username);

        Jwt jwt = jwtDecoder.createToken(userDetails);
        verify(userDetails).getUsername();
        assertThat(jwt.subject()).isEqualTo(username);
        assertThat(jwt.issuedAt()).isInThePast();
        assertThat(jwt.expiresAt()).isInTheFuture();
        assertThat(jwt.tokenValue()).isNotNull();
    }

}
