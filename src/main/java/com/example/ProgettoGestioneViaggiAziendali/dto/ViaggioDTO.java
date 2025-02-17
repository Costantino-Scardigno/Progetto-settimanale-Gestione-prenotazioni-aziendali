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



    public ViaggioDTO(Long idViaggio, LocalDate data, String destinazione, StatoViaggio stato) {
        this.idViaggio = idViaggio;
        this.destinazione = destinazione;
        this.data = data;
        this.stato = stato;

    }
}
