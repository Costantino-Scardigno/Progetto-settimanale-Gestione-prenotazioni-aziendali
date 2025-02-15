package com.example.ProgettoGestioneViaggiAziendali.repository;

import com.example.ProgettoGestioneViaggiAziendali.entity.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, Long> {

}

