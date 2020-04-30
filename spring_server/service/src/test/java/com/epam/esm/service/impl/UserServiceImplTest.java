package com.epam.esm.service.impl;

import com.epam.esm.configuration.PersistenceConfiguration;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityIsAlreadyExistsException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfiguration.class})
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @Transactional
    public void testSignup_ExistLogin() {
        assertTrue(TestTransaction.isActive());

        User user = new User();
        String login = "userLogin";
        String email = "email";
        user.setLogin(login);
        user.setEmail(email);
        Class expectedException = EntityIsAlreadyExistsException.class;
        String expectedMessage = "User with login " + login + " is already exist";

        when(userRepository.getByLogin(login)).thenReturn(Optional.of(user));
        when(userRepository.getByEmail(email)).thenReturn(Optional.empty());

        try {
            userService.signup(user);
        } catch (Exception e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testSignup_ExistEmail() {
        assertTrue(TestTransaction.isActive());

        User user = new User();
        String login = "userLogin";
        String email = "email";
        user.setLogin(login);
        user.setEmail(email);
        Class expectedException = EntityIsAlreadyExistsException.class;
        String expectedMessage = "User with email " + email + " is already exist";

        when(userRepository.getByLogin(login)).thenReturn(Optional.empty());
        when(userRepository.getByEmail(email)).thenReturn(Optional.of(user));

        try {
            userService.signup(user);
        } catch (Exception e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testSignup_ExistEmailAndEmail() {
        assertTrue(TestTransaction.isActive());

        User user = new User();
        String login = "userLogin";
        String email = "email";
        user.setLogin(login);
        user.setEmail(email);
        Class expectedException = EntityIsAlreadyExistsException.class;
        String expectedMessage = "User with login " + login + " email " + email + " is already exist";

        when(userRepository.getByLogin(login)).thenReturn(Optional.of(user));
        when(userRepository.getByEmail(email)).thenReturn(Optional.of(user));

        try {
            userService.signup(user);
        } catch (Exception e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testSignup_UniqueEmailAndEmail() {
        assertTrue(TestTransaction.isActive());

        User user = new User();
        String login = "userLogin";
        String email = "email";
        String password = "password";
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.getByLogin(login)).thenReturn(Optional.empty());
        when(userRepository.getByEmail(email)).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(password)).thenReturn(password);
        when(userRepository.create(user)).thenReturn(user);

        assertEquals(user, userService.signup(user));
    }

    @Test
    public void testGetAll_ListResult() {
        assertFalse(TestTransaction.isActive());

        List<User> users = new ArrayList<>();
        users.add(new User());

        when(userRepository.getAll(User.class)).thenReturn(users);

        assertEquals(users, userService.getAll());
    }

    @Test
    public void testGetAll_PageResult() {
        assertFalse(TestTransaction.isActive());

        Page<User> userPage = mock(Page.class);
        Integer page = 1;
        Integer perPage = 1;

        when(userRepository.getAll(User.class, page, perPage)).thenReturn(userPage);

        assertEquals(userPage, userService.getAll(page, perPage));
    }

    @Test
    public void testGetById_NoExistEntity() {
        assertFalse(TestTransaction.isActive());

        Long id = 1L;
        Class expectedException = NoSuchEntityException.class;
        String expectedMessage = "User with id " + id + " is not exist";

        when(userRepository.getById(User.class, id)).thenReturn(Optional.empty());

        try {
            userService.getById(id);
        } catch (Exception e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testGetById_ExistEntity() {
        assertFalse(TestTransaction.isActive());

        Long id = 1L;
        User user = new User();
        user.setId(id);

        when(userRepository.getById(User.class, id)).thenReturn(Optional.of(user));

        assertEquals(user, userService.getById(id));
    }

    @Test
    public void testGetByLogin_NoExistEntity() {
        assertFalse(TestTransaction.isActive());

        String login = "login";
        Class expectedException = NoSuchEntityException.class;
        String expectedMessage = "User with login " + login + " is not exist";

        when(userRepository.getByLogin(login)).thenReturn(Optional.empty());

        try {
            userService.getByLogin(login);
        } catch (Exception e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testGetByLogin_ExistEntity() {
        assertFalse(TestTransaction.isActive());

        String login = "login";
        User user = new User();
        user.setLogin(login);

        when(userRepository.getByLogin(login)).thenReturn(Optional.of(user));

        assertEquals(user, userService.getByLogin(login));
    }

    @Test
    public void testGetByEmail_NoExistEntity() {
        assertFalse(TestTransaction.isActive());

        String email = "email";
        Class expectedException = NoSuchEntityException.class;
        String expectedMessage = "User with email " + email + " is not exist";

        when(userRepository.getByEmail(email)).thenReturn(Optional.empty());

        try {
            userService.getByEmail(email);
        } catch (Exception e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testGetByEmail_ExistEntity() {
        assertFalse(TestTransaction.isActive());

        String email = "email";
        User user = new User();
        user.setEmail(email);

        when(userRepository.getByEmail(email)).thenReturn(Optional.of(user));

        assertEquals(user, userService.getByEmail(email));
    }
}