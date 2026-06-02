package com.awa.neocare_followUp.service;

import com.awa.neocare_followUp.entity.Alerte;
import com.awa.neocare_followUp.entity.NouveauNe;
import com.awa.neocare_followUp.entity.TypeAlerte;
import com.awa.neocare_followUp.entity.Utilisateur;
import com.awa.neocare_followUp.repository.AlerteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlerteService {

    private final AlerteRepository alerteRepository;

    public AlerteService(AlerteRepository alerteRepository) {
        this.alerteRepository = alerteRepository;
    }

    public void creerAlerte(String titre,
                            String message,
                            TypeAlerte type,
                            NouveauNe bebe,
                            Utilisateur medecin) {

        Alerte alerte = Alerte.builder()
                .titre(titre)
                .message(message)
                .typeAlerte(type)
                .dateCreation(LocalDateTime.now())
                .traite(false)
                .nouveauNe(bebe)
                .medecin(medecin)
                .build();

        alerteRepository.save(alerte);
    }

    public List<Alerte> getAlertesByBebe(Long bebeId) {
        return alerteRepository.findByNouveauNeId(bebeId);
    }
}