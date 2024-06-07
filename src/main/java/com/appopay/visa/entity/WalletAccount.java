package com.appopay.visa.entity;


import jakarta.persistence.*;
import lombok.Data;

@Table(name = "wallet_account")
@Data
@Entity
public class WalletAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
