package com.example.ProgettoGestioneViaggiAziendali.service;


import com.example.ProgettoGestioneViaggiAziendali.Enum.StatoViaggio;
import com.example.ProgettoGestioneViaggiAziendali.entity.Viaggio;
import com.example.ProgettoGestioneViaggiAziendali.repository.ViaggioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ViaggioService {

    @Autowired
    private ViaggioRepository viaggioRepository;

    public Viaggio creareViaggio(Viaggio viaggio) {
        return viaggioRepository.save(viaggio);
    }

    public Optional<Viaggio> modificaStato(Long id, StatoViaggio stato) {
        Viaggio viaggio = viaggioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Viaggio con ID " + id + " non trovato."));
        viaggio.setStato(stato);
        return Optional.of(viaggioRepository.save(viaggio));
    }

    public List<Viaggio> getAllViaggi() {
        return viaggioRepository.findAll();
    }

    public boolean eliminaViaggio(Long id) {
        Optional<Viaggio> optionalViaggio = viaggioRepository.findById(id);
        if (optionalViaggio.isPresent()) {
            viaggioRepository.delete(optionalViaggio.get());
            return true;
        } else {
            return false;
        }
    }

}



