package com.epam.esm.repository;

import com.epam.esm.entity.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID extends Serializable> {
    /**
     * Creates entity in the repository.
     *
     * @param t entity to create.
     * @return entity that was created.
     */
    T create(T t);

    /**
     * Deletes entity with specific identifier from the repository.
     *
     * @param typeVariableClass the type of entity.
     * @param id                entity identifier.
     */
    void delete(Class<T> typeVariableClass, ID id);

    /**
     * Updates entity in the repository.
     *
     * @param t certificate to update.
     * @return updated entity.
     */
    T update(T t);

    /**
     * Returns entity from the repository with specific identifier.
     *
     * @param typeVariableClass the type of entity.
     * @param id                entity identifier.
     * @return entity with specific identifier.
     */
    Optional<T> getById(Class<T> typeVariableClass, ID id);

    /**
     * Returns all entities from the repository.
     *
     * @param typeVariableClass the type of entity.
     * @return list of entity.
     */
    List<T> getAll(Class<T> typeVariableClass);

    /**
     * Returns page with entities from the repository.
     *
     * @param typeVariableClass the type of entity.
     * @param pageNumber        number of page.
     * @param perPage           entity number on one page.
     * @return page with entity.
     */
    Page<T> getAll(Class<T> typeVariableClass, Integer pageNumber, Integer perPage);
}
