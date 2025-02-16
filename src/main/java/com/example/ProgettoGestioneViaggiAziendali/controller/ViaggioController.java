package com.example.ProgettoGestioneViaggiAziendali.controller;


import com.example.ProgettoGestioneViaggiAziendali.Enum.StatoViaggio;
import com.example.ProgettoGestioneViaggiAziendali.entity.Viaggio;
import com.example.ProgettoGestioneViaggiAziendali.service.ViaggioService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ViaggioController {

    @Autowired
    private ViaggioService viaggioService;

    // ENDPOINT per recuperere tutti i viaggi
    @GetMapping("/viaggi")
    public ResponseEntity<?> getAllViaggi() {
        List<Viaggio> viaggi=viaggioService.getAllViaggi();
        if (viaggi.isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LA LISTA DEI VIAGGI E' VUOTA");
        }
        return new ResponseEntity<>(viaggi, HttpStatus.OK);
    }

    // ENDPOINT per creare un viaggio
    @PostMapping("/viaggio")
    public ResponseEntity<?> creaViaggio(@RequestBody @NotNull Viaggio viaggio){
        if (viaggio.getIdViaggio() !=null) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: Il viaggio non deve avere un id quando viene creato.");
        }
        Viaggio creazioneViaggio=viaggioService.creareViaggio(viaggio);
        return new ResponseEntity<>(creazioneViaggio, HttpStatus.CREATED);
    }

    // ENDPOINT per modificare lo stato
    @PutMapping("/viaggio/{id}/stato")
    public ResponseEntity<Viaggio> modificaStatoViaggio(@PathVariable Long id, @RequestBody StatoViaggio stato){
        Optional<Viaggio> viaggioModificato= viaggioService.modificaStato(id,stato);
        return viaggioModificato.map(viaggio -> new ResponseEntity<>(viaggio,HttpStatus.OK))
                .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    // ENDPOINT per eliminare un viaggio

    @DeleteMapping("/viaggio/{id}/elimina")
    public ResponseEntity<String> eliminaViaggio(@PathVariable Long id) {
        boolean eliminato= viaggioService.eliminaViaggio(id);
        if (eliminato){
            return  ResponseEntity.status(HttpStatus.OK).body("Viaggio eliminato con successo!");
        } else {
            return new ResponseEntity<>("Viaggio non trovato!", HttpStatus.NOT_FOUND);
        }
    }
    // ENDPOINT PER ELIMINARE TUTTI I VIAGGI

    @DeleteMapping("/viaggi/eliminaTutti")
    public ResponseEntity<?> eliminaTuttiViaggi() {
        viaggioService.eliminaTuttiViaggi();
        return ResponseEntity.status(HttpStatus.OK).body("TUTTI I VIAGGI SONO STATI ELIMINATI CON SUCCESSO");

    }



}
