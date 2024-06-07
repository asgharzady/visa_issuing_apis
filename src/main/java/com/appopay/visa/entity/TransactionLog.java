package com.appopay.visa.entity;


import jakarta.persistence.*;
import lombok.Data;

@Table(name = "transactionlog")
@Data
@Entity
public class TransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
