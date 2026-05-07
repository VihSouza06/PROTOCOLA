package com.example.aep2b.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "gestores")
@Data
public class GestorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
}
