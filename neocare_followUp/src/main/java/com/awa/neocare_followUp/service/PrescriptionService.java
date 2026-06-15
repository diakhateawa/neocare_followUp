package com.awa.neocare_followUp.service;

import com.awa.neocare_followUp.dto.PrescriptionRequest;
import com.awa.neocare_followUp.dto.PrescriptionResponse;
import com.awa.neocare_followUp.entity.Consultation;
import com.awa.neocare_followUp.entity.Prescription;
import com.awa.neocare_followUp.repository.ConsultationRepository;
import com.awa.neocare_followUp.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final ConsultationRepository consultationRepository;

    public PrescriptionService(PrescriptionRepository prescriptionRepository,
                               ConsultationRepository consultationRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.consultationRepository = consultationRepository;
    }

    // =========================
    // CREATE
    // =========================
    public PrescriptionResponse create(PrescriptionRequest request) {

        if (request.getConsultationId() == null) {
            throw new IllegalArgumentException("consultationId obligatoire");
        }

        Consultation consultation = consultationRepository.findById(request.getConsultationId())
                .orElseThrow(() -> new RuntimeException("Consultation introuvable"));

        Prescription prescription = Prescription.builder()
                .medicaments(request.getMedicaments())
                .posologie(request.getPosologie())
                .duree(request.getDuree())
                .datePrescription(request.getDatePrescription())
                .consultation(consultation)
                .build();

        Prescription saved = prescriptionRepository.save(prescription);

        Prescription full = prescriptionRepository.findByIdWithFetch(saved.getId())
                .orElseThrow(() -> new RuntimeException("Prescription introuvable après création"));

        return mapToResponse(full);
    }

    // =========================
    // GET ALL
    // =========================
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getAll() {
        return prescriptionRepository.findAllWithFetch()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================
    // GET BY ID
    // =========================
    @Transactional(readOnly = true)
    public PrescriptionResponse getById(Long id) {

        Prescription p = prescriptionRepository.findByIdWithFetch(id)
                .orElseThrow(() -> new RuntimeException("Prescription introuvable"));

        return mapToResponse(p);
    }

    // =========================
    // GET BY CONSULTATION
    // =========================
    @Transactional(readOnly = true)
    public PrescriptionResponse getByConsultation(Long consultationId) {

        Prescription p = prescriptionRepository.findByConsultationIdWithFetch(consultationId)
                .orElseThrow(() -> new RuntimeException("Prescription introuvable"));

        return mapToResponse(p);
    }

    // =========================
    // UPDATE
    // =========================
    public PrescriptionResponse update(Long id, PrescriptionRequest request) {

        Prescription p = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription introuvable"));

        Consultation consultation = consultationRepository.findById(request.getConsultationId())
                .orElseThrow(() -> new RuntimeException("Consultation introuvable"));

        p.setMedicaments(request.getMedicaments());
        p.setPosologie(request.getPosologie());
        p.setDuree(request.getDuree());
        p.setDatePrescription(request.getDatePrescription());
        p.setConsultation(consultation);

        Prescription updated = prescriptionRepository.save(p);

        Prescription full = prescriptionRepository.findByIdWithFetch(updated.getId())
                .orElseThrow(() -> new RuntimeException("Prescription introuvable après update"));

        return mapToResponse(full);
    }

    // =========================
    // DELETE
    // =========================
    public void delete(Long id) {
        prescriptionRepository.deleteById(id);
    }

    // =========================
    // MAPPER SAFE (IMPORTANT FIX)
    // =========================
    private PrescriptionResponse mapToResponse(Prescription p) {

        PrescriptionResponse res = new PrescriptionResponse();

        Consultation c = p.getConsultation();

        res.setId(p.getId());
        res.setMedicaments(p.getMedicaments());
        res.setPosologie(p.getPosologie());
        res.setDuree(p.getDuree());
        res.setDatePrescription(p.getDatePrescription());

        if (c != null) {

            res.setConsultationId(c.getId());

            if (c.getNouveauNe() != null) {
                res.setNouveauNeId(c.getNouveauNe().getId());
                res.setNouveauNeNom(c.getNouveauNe().getNom());
            }

            if (c.getMedecin() != null) {
                res.setMedecinId(c.getMedecin().getId());
                res.setMedecinNom(c.getMedecin().getNom());
            }
        }

        return res;
    }
}