package com.example.api.exception;

public class ErrorResponse {
    private String error_message;

    private int code;

    public ErrorResponse(String message, int code) {
        this.code = code;
        this.error_message = message;
    }

    public ErrorResponse(String message) {
        this.error_message = message;
        this.code = 404;
    }

    public String getMessage() {
        return error_message;
    }

    public int getCode() {
        return code;
    }
}
