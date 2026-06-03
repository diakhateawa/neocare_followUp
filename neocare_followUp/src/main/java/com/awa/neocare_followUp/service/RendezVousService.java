package com.awa.neocare_followUp.service;

import com.awa.neocare_followUp.dto.RendezVousRequest;
import com.awa.neocare_followUp.dto.RendezVousResponse;
import com.awa.neocare_followUp.entity.*;
import com.awa.neocare_followUp.repository.NouveauNeRepository;
import com.awa.neocare_followUp.repository.RendezVousRepository;
import com.awa.neocare_followUp.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RendezVousService {
    private final RendezVousRepository rendezVousRepository;
    private final NouveauNeRepository nouveauNeRepository;
    private final UtilisateurRepository utilisateurRepository;

    public RendezVousService(
            RendezVousRepository rendezVousRepository,
            NouveauNeRepository nouveauNeRepository,
            UtilisateurRepository utilisateurRepository) {

        this.rendezVousRepository = rendezVousRepository;
        this.nouveauNeRepository = nouveauNeRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public RendezVousResponse create(RendezVousRequest request) {

        NouveauNe bebe = nouveauNeRepository.findById(request.getNouveauNeId())
                .orElseThrow(() -> new RuntimeException("Bébé introuvable"));

        Utilisateur secretaire = utilisateurRepository.findById(request.getSecretaireId())
                .orElseThrow(() -> new RuntimeException("Secrétaire introuvable"));

        if (!Role.SECRETAIRE.equals(secretaire.getRole())) {
            throw new RuntimeException("Cet utilisateur n'est pas secrétaire");
        }

        RendezVous rdv = RendezVous.builder()
                .dateRdv(request.getDateRdv())
                .motif(request.getMotif())
                .statut(StatutRendezVous.PLANIFIE)
                .nouveauNe(bebe)
                .secretaire(secretaire)
                .build();

        RendezVous saved = rendezVousRepository.save(rdv);

        return mapToResponse(saved);
    }

    public List<RendezVousResponse> getAll() {
        return rendezVousRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<RendezVousResponse> getByBebe(Long bebeId) {
        return rendezVousRepository.findByNouveauNeId(bebeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

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

    public RendezVousResponse getById(Long id) {

        RendezVous rdv = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable"));

        return mapToResponse(rdv);
    }

    public RendezVousResponse update(Long id, RendezVousRequest request) {

        RendezVous rdv = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable"));

        NouveauNe bebe = nouveauNeRepository.findById(request.getNouveauNeId())
                .orElseThrow(() -> new RuntimeException("Bébé introuvable"));

        Utilisateur secretaire = utilisateurRepository.findById(request.getSecretaireId())
                .orElseThrow(() -> new RuntimeException("Secrétaire introuvable"));

        if (!Role.SECRETAIRE.equals(secretaire.getRole())) {
            throw new RuntimeException("Cet utilisateur n'est pas secrétaire");
        }

        rdv.setDateRdv(request.getDateRdv());
        rdv.setMotif(request.getMotif());

        if (request.getStatut() != null) {
            rdv.setStatut(request.getStatut());
        }

        rdv.setNouveauNe(bebe);
        rdv.setSecretaire(secretaire);

        RendezVous updated = rendezVousRepository.save(rdv);

        return mapToResponse(updated);
    }

    public void delete(Long id) {

        RendezVous rdv = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous introuvable"));

        rendezVousRepository.delete(rdv);
    }
}