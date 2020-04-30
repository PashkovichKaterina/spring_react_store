package com.epam.esm.repository.impl;

import com.epam.esm.entity.User;
import com.epam.esm.repository.AbstractCrudRepository;
import com.epam.esm.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class UserRepositoryImpl extends AbstractCrudRepository<User, Long> implements UserRepository {
    private static final String GET_USER_BY_LOGIN = "select user from User user where login = :login";
    private static final String GET_USER_BY_EMAIL = "select user from User user where email = :email";
    private static final String LOGIN = "login";
    private static final String EMAIL = "email";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User update(User user) {
        User updatedUser = entityManager.find(User.class, user.getId());
        updatedUser.setLogin(user.getLogin());
        updatedUser.setPassword(user.getPassword());
        return updatedUser;
    }

    @Override
    public Optional<User> getByLogin(String login) {
        try {
            return Optional.of(
                    entityManager
                            .createQuery(GET_USER_BY_LOGIN, User.class)
                            .setParameter(LOGIN, login)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getByEmail(String email) {
        try {
            return Optional.of(
                    entityManager
                            .createQuery(GET_USER_BY_EMAIL, User.class)
                            .setParameter(EMAIL, email)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}