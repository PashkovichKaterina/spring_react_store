package com.epam.esm.repository;

import com.epam.esm.entity.Page;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudRepository<T, ID extends Serializable> implements CrudRepository<T, ID> {
    @PersistenceContext
    private EntityManager entityManager;

    public T create(T t) {
        entityManager.persist(t);
        return t;
    }

    public void delete(Class<T> typeVariableClass, ID id) {
        T deletedTag = entityManager.find(typeVariableClass, id);
        entityManager.remove(deletedTag);
    }

    public Optional<T> getById(Class<T> typeVariableClass, ID id) {
        return Optional.ofNullable(entityManager.find(typeVariableClass, id));
    }

    public List<T> getAll(Class<T> typeVariableClass) {
        return entityManager.createQuery("select table from " + typeVariableClass.getName() + " table", typeVariableClass)
                .getResultList();
    }

    public Page<T> getAll(Class<T> typeVariableClass, Integer pageNumber, Integer perPage) {
        List<T> entityList = entityManager.createQuery("select table from " + typeVariableClass.getName() + " table", typeVariableClass)
                .setFirstResult((pageNumber - 1) * perPage)
                .setMaxResults(perPage)
                .getResultList();
        return new Page<>(pageNumber, perPage, getEntityCount(typeVariableClass), entityList);
    }

    protected int getEntityCount(Class<T> typeVariableClass) {
        return entityManager.createQuery("select count(table) from " + typeVariableClass.getName() + " table", Long.class)
                .getSingleResult().intValue();
    }
}