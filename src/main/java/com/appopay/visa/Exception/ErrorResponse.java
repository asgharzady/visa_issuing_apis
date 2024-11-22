package com.appopay.visa.Exception;


import lombok.Data;

@Data
public class ErrorResponse {
    private final int status;
    private final String message;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
