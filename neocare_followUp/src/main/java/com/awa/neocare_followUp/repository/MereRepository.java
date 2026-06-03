package com.awa.neocare_followUp.repository;

import com.awa.neocare_followUp.entity.Mere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MereRepository extends JpaRepository<Mere, Long> {
    Optional<Mere> findByNumeroDossier(String numeroDossier);

    @Query("""
        SELECT m FROM Mere m
        LEFT JOIN FETCH m.nouveauNes
        WHERE m.id = :id
    """)
    Optional<Mere> findByIdWithNouveauNes(@Param("id") Long id);

    @Query("""
        SELECT m FROM Mere m
        LEFT JOIN FETCH m.nouveauNes
    """)
    List<Mere> findAllWithNouveauNes();


}