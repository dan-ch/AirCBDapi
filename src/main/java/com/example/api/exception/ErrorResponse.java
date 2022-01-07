package com.example.api.exception;

public class ErrorResponse {
    private String message;

    private int code;

    public ErrorResponse(String message, int code) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(String message) {
        this.message = message;
        this.code = 404;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
