package com.epam.esm.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseErrorDto {
    private String message;
    private String errorCode;
    private List<String> description = new ArrayList<>();

    public ResponseErrorDto() {
    }

    public ResponseErrorDto(String message, String errorCode, List<String> description) {
        this.message = message;
        this.errorCode = errorCode;
        this.description = new ArrayList<>(description);
    }

    public ResponseErrorDto(String message, String errorCode, String description) {
        this.message = message;
        this.errorCode = errorCode;
        this.description.add(description);
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object getDescription() {
        return description.size() > 1 ? description : description.get(0);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setDescription(List<String> description) {
        this.description = new ArrayList<>(description);
    }
}
