package com.example.ProgettoGestioneViaggiAziendali.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "viaggio_id", nullable = false)
    private Viaggio viaggio;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dipendente_id", nullable = false)
    private Dipendente dipendente;

    @Column(nullable = false)
    private LocalDate dataRichiesta;

    private String note;

    @Override
    public String toString() {
        return "Prenotazione{" +
                "id=" + id +
                ", viaggio=" + (viaggio != null ? viaggio.getDestinazione() : "null") +
                ", dipendente=" + (dipendente != null ? dipendente.getUsername() : "null") +
                ", dataRichiesta=" + dataRichiesta +
                ", note='" + note + '\'' +
                '}';
    }
}
