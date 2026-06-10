package com.awa.neocare_followUp.service;

import com.awa.neocare_followUp.dto.RendezVousRequest;
import com.awa.neocare_followUp.dto.RendezVousResponse;
import com.awa.neocare_followUp.entity.*;
import com.awa.neocare_followUp.repository.NouveauNeRepository;
import com.awa.neocare_followUp.repository.RendezVousRepository;
import com.awa.neocare_followUp.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final NouveauNeRepository nouveauNeRepository;
    private final UtilisateurRepository utilisateurRepository;

    public RendezVousService(RendezVousRepository rendezVousRepository,
                             NouveauNeRepository nouveauNeRepository,
                             UtilisateurRepository utilisateurRepository) {
        this.rendezVousRepository = rendezVousRepository;
        this.nouveauNeRepository = nouveauNeRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    // CREATE
    public RendezVousResponse create(RendezVousRequest request) {

        if (request.getDateRdv().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Date de rendez-vous invalide");
        }

        NouveauNe bebe = nouveauNeRepository.findById(request.getNouveauNeId())
                .orElseThrow(() -> new RuntimeException("Bébé introuvable"));

        Utilisateur secretaire = utilisateurRepository.findById(request.getSecretaireId())
                .orElseThrow(() -> new RuntimeException("Secrétaire introuvable"));

        if (!Role.SECRETAIRE.equals(secretaire.getRole())) {
            throw new RuntimeException("Utilisateur non autorisé");
        }

        RendezVous rdv = RendezVous.builder()
                .dateRdv(request.getDateRdv())
                .motif(request.getMotif())
                .statut(StatutRendezVous.PLANIFIE)
                .nouveauNe(bebe)
                .secretaire(secretaire)
                .build();

        return mapToResponse(rendezVousRepository.save(rdv));
    }

    // GET ALL
    public List<RendezVousResponse> getAll() {
        return rendezVousRepository.findAllWithFetch()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY ID
    public RendezVousResponse getById(Long id) {

        RendezVous rdv = rendezVousRepository.findByIdWithFetch(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable"));

        return mapToResponse(rdv);
    }

    // GET BY BÉBÉ
    public List<RendezVousResponse> getByBebe(Long bebeId) {

        return rendezVousRepository.findByNouveauNeId(bebeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // UPDATE
    public RendezVousResponse update(Long id, RendezVousRequest request) {

        RendezVous rdv = rendezVousRepository.findByIdWithFetch(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable"));

        if (request.getDateRdv().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Date invalide");
        }

        NouveauNe bebe = nouveauNeRepository.findById(request.getNouveauNeId())
                .orElseThrow(() -> new RuntimeException("Bébé introuvable"));

        Utilisateur secretaire = utilisateurRepository.findById(request.getSecretaireId())
                .orElseThrow(() -> new RuntimeException("Secrétaire introuvable"));

        if (!Role.SECRETAIRE.equals(secretaire.getRole())) {
            throw new RuntimeException("Utilisateur non autorisé");
        }

        rdv.setDateRdv(request.getDateRdv());
        rdv.setMotif(request.getMotif());
        rdv.setNouveauNe(bebe);
        rdv.setSecretaire(secretaire);

        return mapToResponse(rendezVousRepository.save(rdv));
    }

    // DELETE
    public void delete(Long id) {

        RendezVous rdv = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable"));

        rendezVousRepository.delete(rdv);
    }

    // MAPPING
    private RendezVousResponse mapToResponse(RendezVous rdv) {

        RendezVousResponse res = new RendezVousResponse();

        res.setId(rdv.getId());
        res.setDateRdv(rdv.getDateRdv());
        res.setStatut(rdv.getStatut());
        res.setMotif(rdv.getMotif());

        res.setNouveauNeId(rdv.getNouveauNe().getId());
        res.setNouveauNeNom(rdv.getNouveauNe().getNom());

        res.setSecretaireId(rdv.getSecretaire().getId());
        res.setSecretaireNom(rdv.getSecretaire().getNom());

        return res;
    }
}