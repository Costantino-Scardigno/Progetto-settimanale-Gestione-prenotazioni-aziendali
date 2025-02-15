package com.example.ProgettoGestioneViaggiAziendali.entity;

import com.example.ProgettoGestioneViaggiAziendali.Enum.StatoViaggio;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idViaggio;

    @Column(nullable = false)
    private String destinazione;

    @Column(nullable = false)
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private StatoViaggio stato;

    @OneToMany(mappedBy = "viaggio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prenotazione> prenotazioni;
}
