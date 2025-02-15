package com.example.ProgettoGestioneViaggiAziendali.service;



import com.example.ProgettoGestioneViaggiAziendali.entity.Prenotazione;
import com.example.ProgettoGestioneViaggiAziendali.repository.DipendenteRepository;
import com.example.ProgettoGestioneViaggiAziendali.repository.PrenotazioneRepository;
import com.example.ProgettoGestioneViaggiAziendali.repository.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private ViaggioRepository viaggioRepository;


    public Prenotazione prenotaViaggio(Prenotazione prenotazione) {
        Prenotazione nuovaPrenotazione = new Prenotazione();
        nuovaPrenotazione.setDipendente(prenotazione.getDipendente());
        nuovaPrenotazione.setViaggio(prenotazione.getViaggio());
        nuovaPrenotazione.setDataRichiesta(LocalDate.now());
        nuovaPrenotazione.setNote(prenotazione.getNote());

        return prenotazioneRepository.save(nuovaPrenotazione);
    }


    public List<Prenotazione> getAllPrenotazioni() {
        return prenotazioneRepository.findAll();

}

    public  boolean eliminaPrenotazione(Long id){
        Optional <Prenotazione> prenotazione= prenotazioneRepository.findById(id);
        if (prenotazione.isPresent()){
            prenotazioneRepository.delete(prenotazione.get());
            return true;
        } else {
            return false;
        }
    }



}
