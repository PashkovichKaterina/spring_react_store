package com.epam.esm.service.impl;

import com.epam.esm.entity.Page;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.exception.SignupException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final String NO_EXIST_USER_WITH_ID_MESSAGE = "User with id %d is not exist";
    private static final String NO_EXIST_USER_WITH_LOGIN_MESSAGE = "User with login %s is not exist";
    private static final String NO_EXIST_USER_WITH_EMAIL_MESSAGE = "User with email %s is not exist";

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public User signup(User user) {
        String loginMessage = userRepository.getByLogin(user.getLogin()).isPresent() ? " login " + user.getLogin() : "";
        String emailMessage = userRepository.getByEmail(user.getEmail()).isPresent() ? " email " + user.getEmail() : "";
        if (!loginMessage.isEmpty() && !emailMessage.isEmpty()) {
            throw new SignupException("repeatLoginAndEmail", "User with" + loginMessage + emailMessage + " is already exist");
        }
        if (!loginMessage.isEmpty()) {
            throw new SignupException("repeatLogin", "User with" + loginMessage + " is already exist");
        }
        if (!emailMessage.isEmpty()) {
            throw new SignupException("repeatEmail", "User with" + emailMessage + " is already exist");
        }
        Optional.ofNullable(user.getPassword())
                .ifPresent(password -> user.setPassword(bCryptPasswordEncoder.encode(password)));
        return userRepository.create(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll(User.class);
    }

    @Override
    public Page<User> getAll(Integer page, Integer perPage) {
        return userRepository.getAll(User.class, page, perPage);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(User.class, id)
                .orElseThrow(() -> new NoSuchEntityException(String.format(NO_EXIST_USER_WITH_ID_MESSAGE, id)));
    }

    @Override
    public User getByLogin(String login) {
        return userRepository.getByLogin(login).orElseThrow(
                () -> new NoSuchEntityException(String.format(NO_EXIST_USER_WITH_LOGIN_MESSAGE, login)));
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email).orElseThrow(
                () -> new NoSuchEntityException(String.format(NO_EXIST_USER_WITH_EMAIL_MESSAGE, email)));
    }
}