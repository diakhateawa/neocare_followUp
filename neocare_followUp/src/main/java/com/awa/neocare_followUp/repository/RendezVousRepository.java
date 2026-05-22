package com.awa.neocare_followUp.repository;

import com.awa.neocare_followUp.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByNouveauNeId(Long nouveauNeId);
}