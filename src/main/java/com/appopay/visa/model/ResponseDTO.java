package com.appopay.visa.model;

import lombok.Data;

@Data
public class ResponseDTO {
    private String status = "success";
    private String message;

    public ResponseDTO(String message) {
        this.message = message;
    }
}
