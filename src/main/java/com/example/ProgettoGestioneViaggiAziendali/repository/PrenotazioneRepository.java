package com.example.ProgettoGestioneViaggiAziendali.repository;


import com.example.ProgettoGestioneViaggiAziendali.entity.Dipendente;
import com.example.ProgettoGestioneViaggiAziendali.entity.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    boolean existsByDipendenteAndDataRichiesta(Dipendente dipendente, LocalDate dataRichiesta);
    boolean existsByDipendenteAndViaggio_Data(Dipendente dipendente, LocalDate data);


}
