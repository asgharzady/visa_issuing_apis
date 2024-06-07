package com.appopay.visa.entity;


import jakarta.persistence.*;
import lombok.Data;

@Table(name = "card")
@Data
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
