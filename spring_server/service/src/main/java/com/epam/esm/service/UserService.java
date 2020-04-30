package com.epam.esm.service;

import com.epam.esm.entity.Page;
import com.epam.esm.entity.User;

import java.util.List;

public interface UserService {
    User signup(User user);

    /**
     * Returns all users.
     *
     * @return list of {@code User}.
     */
    List<User> getAll();

    /**
     * Returns page of users.
     *
     * @param page    number of page.
     * @param perPage entity number on one page.
     * @return page of users.
     */
    Page<User> getAll(Integer page, Integer perPage);

    /**
     * Returns user with specific identifier.
     *
     * @param id user identifier.
     * @return user with specific identifier.
     */
    User getById(Long id);

    /**
     * Returns user with specific login.
     *
     * @param login user login.
     * @return user with specific login.
     */
    User getByLogin(String login);

    /**
     * Returns user with specific email.
     *
     * @param email user email.
     * @return user with specific email.
     */
    User getByEmail(String email);
}
