package com.epam.esm.security;

import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityIsAlreadyExistsException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.security.jwt.JwtTokenProvider;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final String LOGIN = "login";
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String TOKEN = "token";
    private static final String NO_ENOUGH_INFORMATION = "Provider can't provide need information for login";

    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    @Autowired
    public CustomAuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Map<String, Object> attr = ((DefaultOAuth2User) authentication.getPrincipal()).getAttributes();

        String login = (String) attr.get(LOGIN);
        String name = (String) attr.get(NAME);
        String email = (String) attr.get(EMAIL);

        if ((login == null && name == null) || email == null) {
            response.getWriter().write(NO_ENOUGH_INFORMATION);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            User user = getUser(login, name, email);
            String token = jwtTokenProvider.createToken(user.getLogin(), user.getEmail());
            response.addHeader(TOKEN, token);
        }
    }

    private User getUser(String login, String name, String email) {
        User user = new User();
        user.setLogin(login == null ? name : login);
        user.setEmail(email);
        User newUser;
        try {
            newUser = userService.signup(user);
        } catch (EntityIsAlreadyExistsException e) {
            newUser = getExistUser(user.getLogin(), user.getEmail());
        }
        return newUser;
    }

    private User getExistUser(String login, String email) {
        try {
            return userService.getByEmail(email);
        } catch (NoSuchEntityException e1) {
            return userService.getByLogin(login);
        }
    }
}