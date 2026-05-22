package com.awa.neocare_followUp.repository;

import com.awa.neocare_followUp.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByUsername(String username);

    Optional<Utilisateur> findByEmail(String email);
}