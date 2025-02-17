package com.example.ProgettoGestioneViaggiAziendali.controller;


import com.example.ProgettoGestioneViaggiAziendali.dto.DipendenteDTO;
import com.example.ProgettoGestioneViaggiAziendali.entity.Dipendente;
import com.example.ProgettoGestioneViaggiAziendali.repository.DipendenteRepository;
import com.example.ProgettoGestioneViaggiAziendali.service.CloudinaryService;
import com.example.ProgettoGestioneViaggiAziendali.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private DipendenteRepository dipendenteRepository;
    @Autowired
    CloudinaryService cloudinaryService;

    // ENDPOINT PER AGGIUNGERE UN DIPENDENTE
    // http://localhost:8080/api/dipendente
    @PostMapping("/dipendente")
    public ResponseEntity<Dipendente> aggiungiDipendente(@RequestBody Dipendente dipendente){
        Dipendente nuovoDipendente = dipendenteService.aggiungiDipendente(dipendente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovoDipendente);
    }

    // ENDPOINT PER MODIFICARE IL DIPENDENTE
    // http://localhost:8080/api/dipendente/{id}

    @PutMapping("/dipendente{id}")
    public ResponseEntity<Dipendente> modificaDipendente(@PathVariable Long id, @RequestBody Dipendente dipendente){
        Dipendente dipendenteMod= dipendenteService.modificaDipendente(id, dipendente);
        return ResponseEntity.status(HttpStatus.CREATED).body(dipendenteMod);
    }

    // ENDPOINT PER ELIMINARE UN DIPENDENTE
    // http://localhost:8080/api/dipendente/{id}/elimina

    @DeleteMapping("/dipendente/{id}/elimina")
    public ResponseEntity<?>  eliminaDipendente(@PathVariable Long id){
        boolean eliminato= dipendenteService.eliminaDipendente(id);
        if (eliminato){
            return new ResponseEntity<>("Dipendente eliminato con sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Dipendente non trovato!", HttpStatus.NOT_FOUND);
        }
    }


    // ENDPOINT PER ELIMINARE TUTTI I DIPENDENTI
    // http://localhost:8080/api/dipendenti/eliminaTutti
    @DeleteMapping("/dipendenti/eliminaTutti")
    public ResponseEntity<?> eliminaTuttiDipendenti() {
        dipendenteService.eliminaTuttiDipendenti();
        return ResponseEntity.status(HttpStatus.OK).body("Tutti i dipendenti sono stati eliminati.");
    }


    // ENDPOINT PER RECUPERARE TUTTI I DIPENDENTI (con info aggiuntive tramite DTO)
    // http://localhost:8080/api/dipendenti

    @GetMapping("/dipendenti")
    public ResponseEntity<?> recuperaDipendenti(){
        List<Dipendente> dipendenti = dipendenteService.getAllDipendenti();
        if (dipendenti.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("LA LISTA DEI DIPENDENTI E' VUOTA");
        }

        // Converti gli oggetti Dipendente in DipendenteDTO con informazioni aggiuntive (numero di prenotazioni e id prenotazioni)
        List<DipendenteDTO> dipendentiDTO = dipendenti.stream()
                .map(dipendente -> new DipendenteDTO(
                        dipendente.getIdDipendente(),
                        dipendente.getUsername(),
                        dipendente.getNome(),
                        dipendente.getCognome(),
                        dipendente.getEmail(),
                        dipendente.getImmagineProfilo(),
                        dipendenteService.getNumeroPrenotazioni(dipendente),
                        dipendenteService.getPrenotazioniId(dipendente)
                ))
                .collect(Collectors.toList());

        return new ResponseEntity<>(dipendentiDTO, HttpStatus.OK);
    }


    //ENDPOINT PER RECUPERARE UN DIPENDENTE TRAMITE ID
    // http://localhost:8080/api/dipendente/{id}
    @GetMapping("dipendente/{id}")
    public ResponseEntity<?> recuperaDipendente(@PathVariable Long id) {
      Dipendente dipendente=dipendenteService.getDipendente(id);
      if (dipendente==null) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DIPENDENTE CON ID:"+ id+" NON ESISTE");
      }
      return ResponseEntity.status(HttpStatus.OK).body(dipendente);
    };






    // Metodo per aggiornare l'immagine del profilo di un dipendente
    // http://localhost:8080/api/dipendente/7/caricaImmagine?file
    @PostMapping("/dipendente/{id}/caricaImmagine")
    public ResponseEntity<?> caricaImmagine(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = cloudinaryService.caricaImmagine(file);
            dipendenteService.aggiornaImmagineDipendente(id, imageUrl);
            return ResponseEntity.ok("Immagine caricata con successo: " + imageUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Errore durante il caricamento dell'immagine");
        }
    }



}
