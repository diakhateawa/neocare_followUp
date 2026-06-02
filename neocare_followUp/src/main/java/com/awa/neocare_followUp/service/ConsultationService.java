package com.awa.neocare_followUp.service;

import com.awa.neocare_followUp.dto.ConsultationRequest;
import com.awa.neocare_followUp.dto.ConsultationResponse;
import com.awa.neocare_followUp.entity.*;
import com.awa.neocare_followUp.repository.ConsultationRepository;
import com.awa.neocare_followUp.repository.NouveauNeRepository;
import com.awa.neocare_followUp.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import com.awa.neocare_followUp.entity.*;
import com.awa.neocare_followUp.repository.*;
@Service
public class ConsultationService {
    private final ConsultationRepository consultationRepository;
    private final NouveauNeRepository nouveauNeRepository;
    private final UtilisateurRepository utilisateurRepository;

    public ConsultationService(ConsultationRepository consultationRepository,
                               NouveauNeRepository nouveauNeRepository,
                               UtilisateurRepository utilisateurRepository) {
        this.consultationRepository = consultationRepository;
        this.nouveauNeRepository = nouveauNeRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    // =========================
    // CREATE CONSULTATION
    // =========================
    public ConsultationResponse create(ConsultationRequest request) {

        // 1. récupérer bébé
        NouveauNe bebe = nouveauNeRepository.findById(request.getNouveauNeId())
                .orElseThrow(() -> new RuntimeException("Bébé introuvable"));

        // 2. récupérer médecin
        Utilisateur medecin = utilisateurRepository.findById(request.getMedecinId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // 3. vérifier rôle médecin
        if (!Role.MEDECIN.equals(medecin.getRole())) {
            throw new RuntimeException("Ce n'est pas un médecin");
        }

        // 4. créer consultation
        Consultation consultation = Consultation.builder()
                .dateConsultation(LocalDateTime.now())
                .temperature(request.getTemperature())
                .poids(request.getPoids())
                .taille(request.getTaille())
                .pc(request.getPc())
                .diagnostic(request.getDiagnostic())
                .observations(request.getObservations())
                .modeAlimentation(request.getModeAlimentation())
                .assistanceRespiratoire(request.getAssistanceRespiratoire())
                .resultatsBiologiques(request.getResultatsBiologiques())
                .nouveauNe(bebe)
                .medecin(medecin)
                .build();

        Consultation saved = consultationRepository.save(consultation);

        return mapToResponse(saved);
    }

    // =========================
    // GET ALL
    // =========================
    public List<ConsultationResponse> getAll() {
        return consultationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================
    // GET BY BEBE
    // =========================
    public List<ConsultationResponse> getByBebe(Long bebeId) {
        return consultationRepository.findByNouveauNeId(bebeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================
    // MAPPING CENTRAL
    // =========================
    private ConsultationResponse mapToResponse(Consultation saved) {

        ConsultationResponse res = new ConsultationResponse();

        res.setId(saved.getId());
        res.setDateConsultation(saved.getDateConsultation());
        res.setTemperature(saved.getTemperature());
        res.setPoids(saved.getPoids());
        res.setTaille(saved.getTaille());
        res.setPc(saved.getPc());
        res.setDiagnostic(saved.getDiagnostic());
        res.setObservations(saved.getObservations());

        res.setNouveauNeId(saved.getNouveauNe().getId());
        res.setNouveauNeNom(saved.getNouveauNe().getNom());

        res.setMedecinId(saved.getMedecin().getId());
        res.setMedecinNom(saved.getMedecin().getNom());

        return res;
    }
}