package com.example.ProgettoGestioneViaggiAziendali.dto;


import lombok.Data;
import java.time.LocalDate;
import com.example.ProgettoGestioneViaggiAziendali.entity.Prenotazione;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PrenotazioneDTO {
    private Long id;
    private Long dipendenteId;
    private LocalDate dataRichiesta;
    private String note;
    private Long viaggioId;
   // private Dipendente dipendenteObj;
   // private ViaggioDTO viaggioDTO;

    // Costruttore per trasformare una Prenotazione in PrenotazioneDTO
    public PrenotazioneDTO(Prenotazione prenotazione) {
        this.id = prenotazione.getId();
        this.dipendenteId = prenotazione.getDipendente().getIdDipendente();
        this.dataRichiesta = prenotazione.getDataRichiesta();
        this.note = prenotazione.getNote();
        this.viaggioId = prenotazione.getViaggio().getIdViaggio();
    }
}

