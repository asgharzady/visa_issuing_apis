package com.appopay.visa.model;

import lombok.Data;

import java.time.Instant;

@Data
public class CustomerDTO {

    private Long id;

    private String FistName;

    private String LastName;

    private String phoneNumber;

    private String email;

    private String address;

    private String gender;

    private Instant createdAt;

    private Instant updatedAt;
}
