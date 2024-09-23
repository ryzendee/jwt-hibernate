package com.ryzend.app.repository;

import com.ryzend.app.entity.UserEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;

    @Override
    public UserEntity save(UserEntity user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        UserEntity result = entityManager.createQuery("""
                        SELECT u
                        FROM UserEntity u
                        WHERE u.username = :username
                        """, UserEntity.class)
                .setParameter("username", username)
                .getSingleResult();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<UserEntity> findById(UUID id) {
        return Optional.ofNullable(entityManager.find(UserEntity.class, id));
    }

    @Override
    public void deleteById(UUID id) {
        entityManager.createQuery("""
                DELETE FROM UserEntity u
                WHERE u.id = :id
                """).setParameter("id", id);
    }
}
