package com.example.ProgettoGestioneViaggiAziendali.controller;

import com.example.ProgettoGestioneViaggiAziendali.dto.PrenotazioneDTO;
import com.example.ProgettoGestioneViaggiAziendali.entity.Dipendente;
import com.example.ProgettoGestioneViaggiAziendali.entity.Prenotazione;
import com.example.ProgettoGestioneViaggiAziendali.entity.Viaggio;
import com.example.ProgettoGestioneViaggiAziendali.repository.DipendenteRepository;
import com.example.ProgettoGestioneViaggiAziendali.repository.ViaggioRepository;
import com.example.ProgettoGestioneViaggiAziendali.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private ViaggioRepository viaggioRepository;

    // ENDPOINT PER CREARE UNA PRENOTAZIONE (RESTITUISCE DTO)
    @PostMapping("/prenotazione")
    public ResponseEntity<?> aggiungiPrenotazione(@RequestBody PrenotazioneDTO prenotazioneDTO) {
        if (prenotazioneDTO.getDipendenteId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERRORE: ID dipendente non fornito");
        }
        if (prenotazioneDTO.getViaggioId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERRORE: ID viaggio non fornito");
        }

        Dipendente dipendente = dipendenteRepository.findById(prenotazioneDTO.getDipendenteId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dipendente non trovato"));

        Viaggio viaggio = viaggioRepository.findById(prenotazioneDTO.getViaggioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viaggio non trovato"));

        boolean esistePrenotazione= prenotazioneService.esistePrenotazionePerData(dipendente,viaggio);
        if (esistePrenotazione){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IL DIPENDENTE HA GIA UNA PRENOTAZIONE PER QUESTA DATA");
        }
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDipendente(dipendente);
        prenotazione.setViaggio(viaggio);
        prenotazione.setDataRichiesta(prenotazioneDTO.getDataRichiesta());
        prenotazione.setNote(prenotazioneDTO.getNote());

        Prenotazione nuovaPrenotazione = prenotazioneService.prenotaViaggio(prenotazione);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PrenotazioneDTO(nuovaPrenotazione));
    }


    //  ENDPOINT PER RECUPERARE LE PRENOTAZIONI (RESTITUISCE LISTA DI DTO)
    @GetMapping("/prenotazioni")
    public ResponseEntity<?> recuperaPrenotazioni() {
        List<Prenotazione> prenotazioni = prenotazioneService.getAllPrenotazioni();
        if (prenotazioni.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LA LISTA DELLE PRENOTAZIONI È VUOTA");
        }

        // Convertire la lista in DTO
        List<PrenotazioneDTO> prenotazioniDTO = prenotazioni.stream()
                .map(PrenotazioneDTO::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(prenotazioniDTO, HttpStatus.OK);
    }

    //  ENDPOINT PER ELIMINARE UNA PRENOTAZIONE
    @DeleteMapping("/prenotazione/{id}/elimina")
    public ResponseEntity<?> eliminaPrenotazione(@PathVariable Long id) {
        boolean eliminato = prenotazioneService.eliminaPrenotazione(id);
        if (eliminato) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Prenotazione eliminata con successo!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prenotazione non trovata!");
        }
    }

    // ENDPOINT per eliminare tutte le prenotazioni
    @DeleteMapping("/prenotazioni/eliminaTutte")
    public ResponseEntity<?> eliminaTutteLePrenotazioni() {
        prenotazioneService.eliminaTutteLePrenotazioni();
        return ResponseEntity.status(HttpStatus.OK).body("Tutte le prenotazioni sono state eliminate.");
    }
}
