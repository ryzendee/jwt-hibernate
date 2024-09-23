package com.ryzend.app.IT;

import com.ryzend.app.entity.UserEntity;
import com.ryzend.app.repository.UserRepository;
import com.ryzend.app.repository.UserRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class UserRepositoryIT extends AbstractBaseIT {

    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeAll
    static void launchContainer() {
        postgreSQLContainer.start();
    }

    @BeforeEach
    void setUserRepository() {
        userRepository = new UserRepositoryImpl(entityManager);
    }

    @Test
    void saveUser_shouldSaveUser() {
        UserEntity userEntity = getUserWithUsername("test");

        userRepository.save(userEntity);
        assertThat(userEntity.getId()).isNotNull();

        UserEntity savedUser = entityManager.find(UserEntity.class, userEntity.getId());
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo(userEntity.getId());
        assertThat(savedUser.getUsername()).isEqualTo(userEntity.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(userEntity.getPassword());
    }

    @Test
    void findUserByUsername_shouldReturnUser() {
        String username = "test";
        UserEntity userEntity = getUserWithUsername(username);

        entityManager.persist(userEntity);

        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        assertThat(userOptional).isPresent();

        UserEntity foundedUser = userOptional.get();
        assertThat(foundedUser.getUsername()).isEqualTo(userEntity.getUsername());
    }

    @Test
    void findUserById_shouldReturnUser() {
        UserEntity userEntity = getUserWithUsername("test");

        entityManager.persist(userEntity);

        Optional<UserEntity> userOptional = userRepository.findById(userEntity.getId());
        assertThat(userOptional).isPresent();

        UserEntity foundedUser = userOptional.get();
        assertThat(foundedUser.getId()).isEqualTo(userEntity.getId());
    }

    @Test
    void deleteById_shouldDeleteUser() {
        UserEntity userEntity = getUserWithUsername("test");

        entityManager.persist(userEntity);

        userRepository.deleteById(userEntity.getId());

        entityManager.clear();
        userEntity = entityManager.find(UserEntity.class, userEntity.getId());
        assertThat(userEntity).isNull();
    }
    private UserEntity getUserWithUsername(String username) {
        return UserEntity.builder()
                .username(username)
                .password("test")
                .build();
    }
}
