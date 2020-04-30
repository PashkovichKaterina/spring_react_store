package com.epam.esm.exception;

public class SignupException extends ServiceException {
    private String exceptionCode;

    public SignupException(String exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public SignupException(String exceptionCode, String message, Throwable e) {
        super(message, e);
        this.exceptionCode = exceptionCode;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}