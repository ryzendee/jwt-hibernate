package com.ryzend.app.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractRepository<T, E> {

    protected final EntityManager entityManager;
    private final Class<E> clazz;

    protected Optional<E> findById(T id) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }

    protected E save(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    protected void deleteById(T id) {
        findById(id)
                .ifPresent(entity -> {
                    entityManager.remove(entity);
                    entityManager.flush();
                });
    }

}
