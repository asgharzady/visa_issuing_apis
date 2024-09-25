package com.appopay.visa.model;

import lombok.Data;

@Data
public class VerifyResponseDTO {
    private String message = "200";
    private String lastLoginTime;

    public VerifyResponseDTO(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
