package com.ryzend.app.dto.response;

import java.util.UUID;

public record CreateUserResponse(UUID id, String username) {
}
