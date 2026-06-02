package com.awa.neocare_followUp.repository;

import com.awa.neocare_followUp.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByNouveauNeId(Long id);
}