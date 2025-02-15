package com.example.ProgettoGestioneViaggiAziendali.repository;


import com.example.ProgettoGestioneViaggiAziendali.entity.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {


}
