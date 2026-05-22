package com.awa.neocare_followUp.repository;

import com.awa.neocare_followUp.entity.DocumentMedical;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentMedicalRepository extends JpaRepository<DocumentMedical, Long> {
    List<DocumentMedical> findByNouveauNeId(Long nouveauNeId);

    List<DocumentMedical> findByConsultationId(Long consultationId);
}