package com.example.ProgettoGestioneViaggiAziendali.dto;

import lombok.Data;

import java.util.List;

@Data
public class DipendenteDTO {
    private Long idDipendente;
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String immagineProfilo;
    private int numeroPrenotazioni;  // Numero di prenotazioni
    private List<Long> prenotazioniIds;  // Lista degli ID delle prenotazioni

    // Costruttore con tutti i campi
    public DipendenteDTO(Long idDipendente, String username, String nome, String cognome, String email, String immagineProfilo, int numeroPrenotazioni, List<Long> prenotazioniIds) {
        this.idDipendente = idDipendente;
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.immagineProfilo = immagineProfilo;
        this.numeroPrenotazioni = numeroPrenotazioni;
        this.prenotazioniIds = prenotazioniIds;
    }
}
