package com.ryzend.app.repository.impl;

import com.ryzend.app.entity.UserEntity;
import com.ryzend.app.repository.AbstractRepository;
import com.ryzend.app.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl extends AbstractRepository<UUID, UserEntity> implements UserRepository {


    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager, UserEntity.class);
    }

    @Override
    public UserEntity save(UserEntity user) {
        return super.save(user);
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
        return super.findById(id);
    }

    @Override
    public void deleteById(UUID id) {
        super.deleteById(id);
    }
}
