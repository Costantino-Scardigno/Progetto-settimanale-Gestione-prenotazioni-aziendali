package com.example.ProgettoGestioneViaggiAziendali.controller;


import com.example.ProgettoGestioneViaggiAziendali.Enum.StatoViaggio;
import com.example.ProgettoGestioneViaggiAziendali.dto.DipendenteDTO;
import com.example.ProgettoGestioneViaggiAziendali.dto.ViaggioDTO;
import com.example.ProgettoGestioneViaggiAziendali.entity.Viaggio;
import com.example.ProgettoGestioneViaggiAziendali.service.ViaggioService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ViaggioController {

    @Autowired
    private ViaggioService viaggioService;

    // ENDPOINT per recuperere tutti i viaggi
    // http://localhost:8080/api/viaggi
    @GetMapping("/viaggi")
    public ResponseEntity<?> getAllViaggi() {
        List<Viaggio> viaggi=viaggioService.getAllViaggi();
        if (viaggi.isEmpty()){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LA LISTA DEI VIAGGI E' VUOTA");
        }

        List<ViaggioDTO> viaggioDTO = viaggi.stream()
                .map(viaggio -> new ViaggioDTO(
                        viaggio.getIdViaggio(),
                        viaggio.getData(),
                        viaggio.getDestinazione(),
                        viaggio.getStato()
                ))
                .collect(Collectors.toList());

        return new ResponseEntity<>(viaggioDTO, HttpStatus.OK);
    }

    // ENDPOINT per creare un viaggio
    // http://localhost:8080/api/viaggio
    @PostMapping("/viaggio")
    public ResponseEntity<?> creaViaggio(@RequestBody @NotNull Viaggio viaggio){
        if (viaggio.getIdViaggio() !=null) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: Il viaggio non deve avere un id quando viene creato.");
        }
        Viaggio creazioneViaggio=viaggioService.creareViaggio(viaggio);
        return new ResponseEntity<>(creazioneViaggio, HttpStatus.CREATED);
    }

    // ENDPOINT per modificare lo stato
    // http://localhost:8080/api/viaggio/{{id}}/stato
    @PutMapping("/viaggio/{id}/stato")
    public ResponseEntity<Viaggio> modificaStatoViaggio(@PathVariable Long id, @RequestBody StatoViaggio stato){
        Optional<Viaggio> viaggioModificato= viaggioService.modificaStato(id,stato);
        return viaggioModificato.map(viaggio -> new ResponseEntity<>(viaggio,HttpStatus.OK))
                .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    // ENDPOINT per eliminare un viaggio
    // http://localhost:8080/api/viaggio/7/elimina

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
    // http://localhost:8080/api/viaggi/eliminaTutti

    @DeleteMapping("/viaggi/eliminaTutti")
    public ResponseEntity<?> eliminaTuttiViaggi() {
        viaggioService.eliminaTuttiViaggi();
        return ResponseEntity.status(HttpStatus.OK).body("TUTTI I VIAGGI SONO STATI ELIMINATI CON SUCCESSO");

    }



}
