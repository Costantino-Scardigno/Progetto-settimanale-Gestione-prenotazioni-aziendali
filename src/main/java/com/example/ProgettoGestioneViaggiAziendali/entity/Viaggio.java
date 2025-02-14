package com.example.ProgettoGestioneViaggiAziendali.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idViaggio;

    @Column(nullable = false)
    private String descrizione;
    private LocalDate dataViaggio;


    private String stato;

}
