package com.ryzend.app.dto.request;

public record CreateUserRequest(String username, String password, String passwordConfirmation) {
}
