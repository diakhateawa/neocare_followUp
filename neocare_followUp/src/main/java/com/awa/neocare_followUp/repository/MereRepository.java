package com.awa.neocare_followUp.repository;

import com.awa.neocare_followUp.entity.Mere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MereRepository extends JpaRepository<Mere, Long> {
    Optional<Mere> findByNumeroDossier(String numeroDossier);
}