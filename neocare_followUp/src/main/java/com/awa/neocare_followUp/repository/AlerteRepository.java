package com.awa.neocare_followUp.repository;

import com.awa.neocare_followUp.entity.Alerte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlerteRepository extends JpaRepository<Alerte, Long> {

    List<Alerte> findByTraite(boolean traite);
    List<Alerte> findByNouveauNeId(Long id);
}