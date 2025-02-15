package com.example.ProgettoGestioneViaggiAziendali.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Dipendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDipendente;

    @Column(nullable = false, unique = true)
    private String username;

    private String nome;
    private String cognome;

    @Column(unique = true, nullable = false)
    private String email;

    @Lob
    private byte[] immagineProfilo;

    @OneToMany(mappedBy = "dipendente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prenotazione> prenotazioni;
}
