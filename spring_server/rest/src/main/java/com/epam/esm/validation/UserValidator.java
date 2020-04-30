package com.epam.esm.validation;

import com.epam.esm.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {
    private static final String LOGIN = "login";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        UserDto user = (UserDto) obj;
        if (!isValidLogin(user.getLogin())) {
            errors.rejectValue(LOGIN, LOGIN);
        }
        if (!isValidPassword(user.getPassword())) {
            errors.rejectValue(PASSWORD, PASSWORD);
        }
        if (!isValidEmail(user.getEmail())) {
            errors.rejectValue(EMAIL, EMAIL);
        }
    }

    private boolean isValidLogin(String login) {
        if (Optional.ofNullable(login).isPresent()) {
            Pattern p = Pattern.compile("^\\w{1,50}$");
            Matcher m = p.matcher(login);
            return m.matches();
        }
        return false;
    }

    private boolean isValidPassword(String password) {
        if (Optional.ofNullable(password).isPresent()) {
            Pattern p = Pattern.compile("(?=.*[0-9])(?=.*[A-z])[0-9A-z]{8,}");
            Matcher m = p.matcher(password);
            return m.matches();
        }
        return false;
    }

    private boolean isValidEmail(String email) {
        if (Optional.ofNullable(email).isPresent()) {
            Pattern p = Pattern.compile("^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$");
            Matcher m = p.matcher(email);
            return m.matches();
        }
        return false;
    }
}