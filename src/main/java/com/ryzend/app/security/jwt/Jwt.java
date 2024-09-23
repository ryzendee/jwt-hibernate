package com.ryzend.app.security.jwt;

import java.util.Date;


public record Jwt(String subject, String tokenValue, Date issuedAt, Date expiresAt) {
}
