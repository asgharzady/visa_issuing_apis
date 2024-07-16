package com.appopay.visa.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Table(name = "customers")
@Data
@Entity
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String FistName;

    private String LastName;

    private String phoneNumber;

    private String email;

    private String address;

    private String ofac;

    private String gender;

    private Instant createdAt;

    private Instant updatedAt;


}
