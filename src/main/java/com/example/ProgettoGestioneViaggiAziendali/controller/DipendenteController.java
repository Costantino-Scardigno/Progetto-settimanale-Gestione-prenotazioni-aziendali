package com.example.ProgettoGestioneViaggiAziendali.controller;


import com.example.ProgettoGestioneViaggiAziendali.entity.Dipendente;
import com.example.ProgettoGestioneViaggiAziendali.repository.DipendenteRepository;
import com.example.ProgettoGestioneViaggiAziendali.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private DipendenteRepository dipendenteRepository;

    // METODO PER AGGIUNGERE UN DIPENDENTE
    @PostMapping("/dipendente")
    public ResponseEntity<Dipendente> aggiungiDipendente(@RequestBody Dipendente dipendente){
        Dipendente nuovoDipendente = dipendenteService.aggiungiDipendente(dipendente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuovoDipendente);
    }

    // METODO PER MODIFICARE IL DIPENDENTE

    @PutMapping("/dipendente{id}")
    public ResponseEntity<Dipendente> modificaDipendente(@PathVariable Long id, @RequestBody Dipendente dipendente){
        Dipendente dipendenteMod= dipendenteService.modificaDipendente(id, dipendente);
        return ResponseEntity.status(HttpStatus.CREATED).body(dipendenteMod);
    }

    // METODO PER ELIMINARE UN DIPENDENTE

    @DeleteMapping("/dipendente/{id}/elimina")
    public ResponseEntity<?>  eliminaDipendente(@PathVariable Long id){
        boolean eliminato= dipendenteService.eliminaDipendente(id);
        if (eliminato){
            return new ResponseEntity<>("Dipendente eliminato con sucesso!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Dipendente non trovato!", HttpStatus.NOT_FOUND);
        }
    }

    // METODO PER RECUPERARE TUTTI I DIPENDENTI

    @GetMapping("/dipendenti")
    public ResponseEntity<List<Dipendente>> recuperaDipendenti(){
        List<Dipendente> dipendenti = dipendenteService.getAllDipendenti();
        if (dipendenti.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Message", "LA LISTA DEI DIPENDENTI E' VUOTA.");
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dipendenti, HttpStatus.OK);
    }


}
