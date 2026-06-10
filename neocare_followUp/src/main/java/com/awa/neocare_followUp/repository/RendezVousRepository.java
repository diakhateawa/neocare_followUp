package com.awa.neocare_followUp.repository;

import com.awa.neocare_followUp.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByNouveauNeId(Long nouveauNeId);

    @Query("""
        SELECT r FROM RendezVous r
        JOIN FETCH r.nouveauNe
        JOIN FETCH r.secretaire
        WHERE r.id = :id
    """)
    Optional<RendezVous> findByIdWithFetch(@Param("id") Long id);

    @Query("""
        SELECT r FROM RendezVous r
        JOIN FETCH r.nouveauNe
        JOIN FETCH r.secretaire
    """)
    List<RendezVous> findAllWithFetch();
}