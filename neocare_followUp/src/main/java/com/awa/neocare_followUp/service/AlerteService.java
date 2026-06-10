package com.awa.neocare_followUp.service;

import com.awa.neocare_followUp.dto.AlerteRequest;
import com.awa.neocare_followUp.dto.AlerteResponse;
import com.awa.neocare_followUp.entity.*;
import com.awa.neocare_followUp.repository.AlerteRepository;
import com.awa.neocare_followUp.repository.NouveauNeRepository;
import com.awa.neocare_followUp.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlerteService {

    private final AlerteRepository alerteRepository;
    private final NouveauNeRepository nouveauNeRepository;
    private final UtilisateurRepository utilisateurRepository;

    public AlerteService(AlerteRepository alerteRepository,
                         NouveauNeRepository nouveauNeRepository,
                         UtilisateurRepository utilisateurRepository) {
        this.alerteRepository = alerteRepository;
        this.nouveauNeRepository = nouveauNeRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public AlerteResponse create(AlerteRequest request) {

        NouveauNe bebe = nouveauNeRepository.findById(request.getNouveauNeId())
                .orElseThrow(() -> new RuntimeException("Bébé introuvable"));

        Utilisateur medecin = utilisateurRepository.findById(request.getMedecinId())
                .orElseThrow(() -> new RuntimeException("Médecin introuvable"));

        Alerte alerte = Alerte.builder()
                .titre(request.getTitre())
                .message(request.getMessage())
                .typeAlerte(request.getTypeAlerte())
                .nouveauNe(bebe)
                .medecin(medecin)
                .build();

        return mapToResponse(alerteRepository.save(alerte));
    }

    public List<AlerteResponse> getAll() {
        return alerteRepository.findAllWithFetch()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<AlerteResponse> getByBebe(Long id) {
        return alerteRepository.findByNouveauNeIdWithFetch(id)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AlerteResponse marquerCommeTraite(Long id) {

        Alerte alerte = alerteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerte introuvable"));

        alerte.setTraite(true);

        return mapToResponse(alerteRepository.save(alerte));
    }

    public void delete(Long id) {
        alerteRepository.deleteById(id);
    }

    private AlerteResponse mapToResponse(Alerte a) {

        AlerteResponse res = new AlerteResponse();

        res.setId(a.getId());
        res.setTitre(a.getTitre());
        res.setMessage(a.getMessage());
        res.setTypeAlerte(a.getTypeAlerte());
        res.setDateCreation(a.getDateCreation());
        res.setTraite(a.isTraite());

        res.setNouveauNeId(a.getNouveauNe().getId());
        res.setNouveauNeNom(a.getNouveauNe().getNom());

        res.setMedecinId(a.getMedecin().getId());
        res.setMedecinNom(a.getMedecin().getNom());

        return res;
    }
}