package com.example.ProgettoGestioneViaggiAziendali.controller;

import com.example.ProgettoGestioneViaggiAziendali.dto.PrenotazioneDTO;
import com.example.ProgettoGestioneViaggiAziendali.entity.Dipendente;
import com.example.ProgettoGestioneViaggiAziendali.entity.Prenotazione;
import com.example.ProgettoGestioneViaggiAziendali.entity.Viaggio;
import com.example.ProgettoGestioneViaggiAziendali.repository.DipendenteRepository;
import com.example.ProgettoGestioneViaggiAziendali.repository.PrenotazioneRepository;
import com.example.ProgettoGestioneViaggiAziendali.repository.ViaggioRepository;
import com.example.ProgettoGestioneViaggiAziendali.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PrenotazioneController {

    @Autowired
    PrenotazioneService prenotazioneService;

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private ViaggioRepository viaggioRepository;
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;



    // METODO PER CREARE UNA PRENOTAZIONE
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


        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDipendente(dipendente);
        prenotazione.setViaggio(viaggio);
        prenotazione.setDataRichiesta(prenotazioneDTO.getDataRichiesta());
        prenotazione.setNote(prenotazioneDTO.getNote());

        Prenotazione nuovaPrenotazione = prenotazioneService.prenotaViaggio(prenotazione);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuovaPrenotazione);
    }

    // METODO PER RECUPERARE LE PRENOTAZIONI
    @GetMapping("/prenotazioni")
    public ResponseEntity<?> recuperaDipendenti(){
        List<Prenotazione> prenotazioni = prenotazioneService.getAllPrenotazioni();
        if (prenotazioni.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LA LISTA DELLE PRENOTAZIONI E' VUOTA");
        }
        return new ResponseEntity<>(prenotazioni, HttpStatus.OK);
    }

    // METODO PER ELIMINARE UNA PRENOTAZIONE
    @DeleteMapping("/prenotazione/{id}/elimina")
    public ResponseEntity<?>  eliminaPrenotazione(@PathVariable Long id){
        boolean eliminato= prenotazioneService.eliminaPrenotazione(id);
        if (eliminato){
            return new ResponseEntity<>("Prenotazione eliminata con sucesso!", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Prenotazione non trovato!", HttpStatus.NOT_FOUND);
        }
    }







}
