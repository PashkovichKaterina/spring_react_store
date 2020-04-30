package com.epam.esm.handler;

import com.epam.esm.dto.ResponseErrorDto;
import com.epam.esm.exception.EntityIsAlreadyExistsException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.exception.SignupException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MainExceptionHandler extends ResponseEntityExceptionHandler {
    private static String INVALID_ID_TYPE = "invalidIdType";
    private static String INVALID_ID_TYPE_MESSAGE = "Id should be a number";
    private static String GENERAL_EXCEPTION = "generalException";
    private static String NO_SUCH_ENTITY = "noSuchEntity";
    private static String EXIST_ENTITY = "existEntity";
    private static String AUTHENTICATION_EXCEPTION = "authenticationException";
    private static String AUTHENTICATION_EXCEPTION_MESSAGE = "Invalid login or password";

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(status.getReasonPhrase(), INVALID_ID_TYPE, INVALID_ID_TYPE_MESSAGE);
        return this.handleExceptionInternal(ex, responseErrorDto, headers, status, request);
    }

    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity handleExistEntity(ServiceException e, WebRequest request) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), GENERAL_EXCEPTION, e.getMessage());
        return handleExceptionInternal(e, responseErrorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {SignupException.class})
    public ResponseEntity handleSignupException(SignupException e, WebRequest request) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getExceptionCode(), e.getMessage());
        return handleExceptionInternal(e, responseErrorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {NoSuchEntityException.class})
    public ResponseEntity handleNotExistEntity(ServiceException e, WebRequest request) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.NOT_FOUND.getReasonPhrase(), NO_SUCH_ENTITY, e.getMessage());
        return handleExceptionInternal(e, responseErrorDto, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {EntityIsAlreadyExistsException.class})
    public ResponseEntity handleEntityIsAlreadyExistsException(EntityIsAlreadyExistsException e, WebRequest request) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.NOT_FOUND.getReasonPhrase(), EXIST_ENTITY, e.getMessage());
        return handleExceptionInternal(e, responseErrorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity handleAuthenticationException(AuthenticationException e, WebRequest request) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.NOT_FOUND.getReasonPhrase(), AUTHENTICATION_EXCEPTION, AUTHENTICATION_EXCEPTION_MESSAGE);
        return handleExceptionInternal(e, responseErrorDto, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException e, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation c : e.getConstraintViolations()) {
            errors.add(c.getMessage());
        }
        ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), GENERAL_EXCEPTION, errors);
        return handleExceptionInternal(e, responseErrorDto, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}