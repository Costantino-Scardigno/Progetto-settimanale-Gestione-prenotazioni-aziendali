package com.example.ProgettoGestioneViaggiAziendali.service;



import com.example.ProgettoGestioneViaggiAziendali.entity.Dipendente;
import com.example.ProgettoGestioneViaggiAziendali.entity.Prenotazione;
import com.example.ProgettoGestioneViaggiAziendali.entity.Viaggio;
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

    // METODO PER CREARE UNA PRENOTAZIONE
    public Prenotazione prenotaViaggio(Prenotazione prenotazione) {
        return prenotazioneRepository.save(prenotazione);
    }
    // METODO PER VERIFICARE SE UN UTENTE HA GIA PRENOTATO PER UN VIAGGIO
    public boolean esistePrenotazionePerData(Dipendente dipendente, Viaggio viaggio) {
        return prenotazioneRepository.existsByDipendenteAndViaggio_Data(dipendente, viaggio.getData());
    }

    // METODO PER RICEVERE TUTTE LE PRENOTAZIONI
    public List<Prenotazione> getAllPrenotazioni() {
        return prenotazioneRepository.findAll();

}
   // METODO PER ELIMINARE UNA PRENOTAZIONE
    public  boolean eliminaPrenotazione(Long id){
        Optional <Prenotazione> prenotazione= prenotazioneRepository.findById(id);
        if (prenotazione.isPresent()){
            prenotazioneRepository.delete(prenotazione.get());
            return true;
        } else {
            return false;
        }
    }

    // Metodo per eliminare tutte le prenotazioni
    public void eliminaTutteLePrenotazioni() {
        prenotazioneRepository.deleteAll();
    }



}
