package com.appopay.visa.entity;


import jakarta.persistence.*;
import lombok.Data;

@Table(name = "auditLog")
@Data
@Entity
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
