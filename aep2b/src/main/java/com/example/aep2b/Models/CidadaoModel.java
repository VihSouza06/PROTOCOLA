package com.example.aep2b.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cidadoes")
@Data
public class CidadaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
}
