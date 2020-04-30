package com.epam.esm.repository;

import com.epam.esm.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Returns user with specific login from repository.
     *
     * @param login user login.
     * @return user with specific login.
     */
    Optional<User> getByLogin(String login);

    /**
     * Returns user with specific email from repository.
     *
     * @param email user email.
     * @return user with specific email.
     */
    Optional<User> getByEmail(String email);
}
