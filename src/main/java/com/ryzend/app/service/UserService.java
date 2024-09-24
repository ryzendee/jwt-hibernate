package com.ryzend.app.service;

import com.ryzend.app.dto.request.CreateUserRequest;
import com.ryzend.app.dto.response.CreateUserResponse;
import com.ryzend.app.entity.UserEntity;
import com.ryzend.app.exception.UsernameExistsException;
import com.ryzend.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) throws UsernameExistsException {
        UserEntity user = mapFromRequest(request);
        save(user);

        return mapToResponse(user);
    }

    private UserEntity mapFromRequest(CreateUserRequest request) {
        return UserEntity.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();
    }

    private UserEntity save(UserEntity user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new UsernameExistsException("This username is already exists");
        }
    }

    private CreateUserResponse mapToResponse(UserEntity user) {
        return new CreateUserResponse(user.getId(), user.getUsername());
    }
}
