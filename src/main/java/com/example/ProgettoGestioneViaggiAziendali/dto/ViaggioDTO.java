package com.example.ProgettoGestioneViaggiAziendali.dto;

import com.example.ProgettoGestioneViaggiAziendali.Enum.StatoViaggio;
import com.example.ProgettoGestioneViaggiAziendali.entity.Viaggio;
import lombok.Data;


import java.time.LocalDate;

@Data
public class ViaggioDTO {

    private Long idViaggio;
    private String destinazione;
    private LocalDate data;
    private StatoViaggio stato;

    // Costruttore per trasformare un Viaggio in ViaggioDTO
    public ViaggioDTO(Viaggio viaggio) {
        this.idViaggio = viaggio.getIdViaggio();
        this.destinazione = viaggio.getDestinazione();
        this.data = viaggio.getData();
        this.stato = viaggio.getStato();
    }
}
