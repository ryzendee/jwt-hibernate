package com.ryzend.app.repository;

import com.ryzend.app.entity.UserEntity;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    UserEntity save(UserEntity user);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findById(UUID Id);
    void deleteById(UUID id);
}
