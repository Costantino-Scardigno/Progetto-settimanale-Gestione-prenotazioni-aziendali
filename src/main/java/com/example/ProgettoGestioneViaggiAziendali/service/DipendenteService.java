package com.example.ProgettoGestioneViaggiAziendali.service;
import java.util.stream.Collectors;
import com.example.ProgettoGestioneViaggiAziendali.entity.Dipendente;
import com.example.ProgettoGestioneViaggiAziendali.repository.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DipendenteService {


    @Autowired
    private DipendenteRepository dipendenteRepository;

    public Dipendente aggiungiDipendente(Dipendente dipendente) {
        return dipendenteRepository.save(dipendente);
    }

    public List<Dipendente> getAllDipendenti() {
        return dipendenteRepository.findAll();
    }

    public Dipendente getDipendente(Long id) {
        Dipendente dipendente= dipendenteRepository.findById(id).orElse(null);
       return dipendente;

    };

    public Dipendente modificaDipendente(Long id, Dipendente dipendenteModificato) {
        Optional<Dipendente> dipendenteEsistenteOpt = dipendenteRepository.findById(id);
        if (dipendenteEsistenteOpt.isPresent()) {
            Dipendente dipendenteEsistente = dipendenteEsistenteOpt.get();
            dipendenteEsistente.setNome(dipendenteModificato.getNome());
            dipendenteEsistente.setCognome(dipendenteModificato.getCognome());
            dipendenteEsistente.setEmail(dipendenteModificato.getEmail());
            dipendenteEsistente.setUsername(dipendenteModificato.getUsername());
            dipendenteEsistente.setImmagineProfilo(dipendenteModificato.getImmagineProfilo());
            return dipendenteRepository.save(dipendenteEsistente);
        }
        return null;
    }


    public boolean eliminaDipendente(Long id){
        Optional<Dipendente> dipendente = dipendenteRepository.findById(id);
        if (dipendente.isPresent()){
            dipendenteRepository.delete(dipendente.get());
            return true;
        } else {
            return false;
        }
    }

    public void eliminaTuttiDipendenti() {
        dipendenteRepository.deleteAll();
    }

    // Metodo per ottenere il numero di prenotazioni di un dipendente
    public int getNumeroPrenotazioni(Dipendente dipendente) {
        return dipendente.getPrenotazioni() != null ? dipendente.getPrenotazioni().size() : 0;
    }

    // Metodo per ottenere gli ID delle prenotazioni di un dipendente
    public List<Long> getPrenotazioniId(Dipendente dipendente) {
        return dipendente.getPrenotazioni().stream()
                .map(prenotazione -> prenotazione.getId())
                .collect(Collectors.toList());
    }

    // metodo per inserire un immagine
    public void aggiornaImmagineDipendente(Long id, String imageUrl) {
        Dipendente dipendente = dipendenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dipendente non trovato"));
        dipendente.setImmagineProfilo(imageUrl);
        dipendenteRepository.save(dipendente);
    }


}
