package com.appopay.visa.entity;


import jakarta.persistence.*;
import lombok.Data;

@Table(name = "pandata")
@Data
@Entity
public class PanData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
