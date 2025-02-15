package com.example.ProgettoGestioneViaggiAziendali.service;

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
}
