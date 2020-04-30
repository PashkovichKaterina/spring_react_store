package com.epam.esm.controller;

import com.epam.esm.converter.UserConverter;
import com.epam.esm.dto.ResponseErrorDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.security.jwt.JwtTokenProvider;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ResponseMessage;
import com.epam.esm.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {
    private static final String JWT_TYPE = "type";
    private static final String TOKEN = "token";
    private static final String SIGNUP_EXCEPTION = "signupException";

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserConverter userConverter;
    private UserService userService;
    private UserValidator userValidator;
    private ResponseMessage responseMessage;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                                    UserConverter userConverter, UserService userService,
                                    UserValidator userValidator, ResponseMessage responseMessage) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userConverter = userConverter;
        this.userService = userService;
        this.userValidator = userValidator;
        this.responseMessage = responseMessage;
    }

    @InitBinder
    protected void initBinderCertificate(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserDto userDto) {
        String username = userDto.getEmail() == null ? userDto.getLogin() : userDto.getEmail();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, userDto.getPassword()));
        String token = jwtTokenProvider.createToken(userDto.getLogin(), userDto.getEmail());
        Map<Object, Object> response = new HashMap<>();
        response.put(JWT_TYPE, JwtTokenProvider.BEARER);
        response.put(TOKEN, token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@Validated @RequestBody UserDto userDto,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), SIGNUP_EXCEPTION,
                    responseMessage.formErrorMessage(bindingResult));
            return ResponseEntity.badRequest().body(responseErrorDto);
        }
        UserDto createdUser = userConverter.toUserDto(userService.signup(userConverter.toUser(userDto)));
        URI createdResourceAddress = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CertificateController.class)
                        .getCertificateById(createdUser.getId()))
                .toUri();
        return ResponseEntity.created(createdResourceAddress).body(createdUser);
    }
}