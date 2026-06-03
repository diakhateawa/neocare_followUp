package com.awa.neocare_followUp.repository;

import com.awa.neocare_followUp.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findByNouveauNeId(Long id);

    @Query("""
        SELECT c FROM Consultation c
        JOIN FETCH c.nouveauNe
        JOIN FETCH c.medecin
        WHERE c.nouveauNe.id = :id
    """)
    List<Consultation> findByNouveauNeIdWithFetch(@Param("id") Long id);
}