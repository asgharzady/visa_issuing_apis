package com.appopay.visa.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

@Table(name = "iam")
@Data
@Entity
public class IamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;

    private Instant createdAt;

    private Instant updatedAt;
}
