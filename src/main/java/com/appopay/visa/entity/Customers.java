package com.appopay.visa.entity;


import jakarta.persistence.*;
import lombok.Data;

@Table(name = "customers")
@Data
@Entity
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
