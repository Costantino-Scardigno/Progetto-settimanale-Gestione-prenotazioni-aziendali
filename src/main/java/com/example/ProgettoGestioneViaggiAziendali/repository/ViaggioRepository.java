package com.example.ProgettoGestioneViaggiAziendali.repository;

import com.example.ProgettoGestioneViaggiAziendali.entity.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, Long> {
}

