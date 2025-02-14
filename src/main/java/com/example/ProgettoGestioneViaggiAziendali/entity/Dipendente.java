package com.example.ProgettoGestioneViaggiAziendali.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Dipendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDipendente;
    @Column(nullable = false, unique = true)
    private String username;
    private String nome;
    private String cognome;

    @Column (unique = true)
    private String email;

}
