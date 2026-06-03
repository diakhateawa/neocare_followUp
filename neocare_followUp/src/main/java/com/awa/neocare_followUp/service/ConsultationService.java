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
import org.springframework.transaction.annotation.Transactional;

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

    // CREATE
    public ConsultationResponse create(ConsultationRequest request) {

        NouveauNe bebe = nouveauNeRepository.findById(request.getNouveauNeId())
                .orElseThrow(() -> new RuntimeException("Bébé introuvable"));

        Utilisateur medecin = utilisateurRepository.findById(request.getMedecinId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (!Role.MEDECIN.equals(medecin.getRole())) {
            throw new RuntimeException("Ce n'est pas un médecin");
        }

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

    // GET ALL
    @Transactional(readOnly = true)
    public List<ConsultationResponse> getAll() {
        return consultationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY BEBE
    public List<ConsultationResponse> getByBebe(Long id) {
        return consultationRepository.findByNouveauNeIdWithFetch(id)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // MAPPING
    private ConsultationResponse mapToResponse(Consultation c) {

        ConsultationResponse res = new ConsultationResponse();

        res.setId(c.getId());
        res.setDateConsultation(c.getDateConsultation());
        res.setTemperature(c.getTemperature());
        res.setPoids(c.getPoids());
        res.setTaille(c.getTaille());
        res.setPc(c.getPc());
        res.setDiagnostic(c.getDiagnostic());
        res.setObservations(c.getObservations());

        res.setNouveauNeId(c.getNouveauNe().getId());
        res.setNouveauNeNom(c.getNouveauNe().getNom());

        res.setMedecinId(c.getMedecin().getId());
        res.setMedecinNom(c.getMedecin().getNom());

        return res;
    }
}