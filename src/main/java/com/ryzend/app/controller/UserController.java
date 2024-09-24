package com.ryzend.app.controller;

import com.ryzend.app.dto.request.CreateUserRequest;
import com.ryzend.app.dto.response.CreateUserResponse;
import com.ryzend.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public CreateUserResponse saveUser(@RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }
}
