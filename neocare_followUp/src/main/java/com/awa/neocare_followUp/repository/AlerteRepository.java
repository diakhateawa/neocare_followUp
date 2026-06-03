package com.awa.neocare_followUp.repository;

import com.awa.neocare_followUp.entity.Alerte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlerteRepository extends JpaRepository<Alerte, Long> {

    @Query("""
        SELECT a FROM Alerte a
        JOIN FETCH a.nouveauNe
        JOIN FETCH a.medecin
        WHERE a.nouveauNe.id = :id
    """)
    List<Alerte> findByNouveauNeIdWithFetch(@Param("id") Long id);

    @Query("""
        SELECT a FROM Alerte a
        JOIN FETCH a.nouveauNe
        JOIN FETCH a.medecin
    """)
    List<Alerte> findAllWithFetch();

    @Query("SELECT a FROM Alerte a JOIN FETCH a.nouveauNe JOIN FETCH a.medecin")
    List<Alerte> findAllWithRelations();
}