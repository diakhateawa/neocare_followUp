package com.awa.neocare_followUp.repository;

import com.awa.neocare_followUp.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    Optional<Prescription> findByConsultationId(Long consultationId);

    // GET BY ID avec tout chargé
    @Query("""
        SELECT p FROM Prescription p
        JOIN FETCH p.consultation c
        JOIN FETCH c.nouveauNe
        JOIN FETCH c.medecin
        WHERE p.id = :id
    """)
    Optional<Prescription> findByIdWithFetch(Long id);

    // GET BY CONSULTATION avec tout chargé
    @Query("""
        SELECT p FROM Prescription p
        JOIN FETCH p.consultation c
        JOIN FETCH c.nouveauNe
        JOIN FETCH c.medecin
        WHERE c.id = :consultationId
    """)
    Optional<Prescription> findByConsultationIdWithFetch(Long consultationId);

    // GET ALL propre (important pour éviter Lazy error)
    @Query("""
        SELECT DISTINCT p FROM Prescription p
        JOIN FETCH p.consultation c
        JOIN FETCH c.nouveauNe
        JOIN FETCH c.medecin
    """)
    List<Prescription> findAllWithFetch();
}