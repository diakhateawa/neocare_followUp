package com.awa.neocare_followUp.service;

import com.awa.neocare_followUp.dto.DocumentMedicalRequest;
import com.awa.neocare_followUp.dto.DocumentMedicalResponse;
import com.awa.neocare_followUp.entity.Consultation;
import com.awa.neocare_followUp.entity.DocumentMedical;
import com.awa.neocare_followUp.entity.NouveauNe;
import com.awa.neocare_followUp.entity.Utilisateur;
import com.awa.neocare_followUp.repository.ConsultationRepository;
import com.awa.neocare_followUp.repository.DocumentMedicalRepository;
import com.awa.neocare_followUp.repository.NouveauNeRepository;
import com.awa.neocare_followUp.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentMedicalService {

    private final DocumentMedicalRepository repository;
    private final NouveauNeRepository nouveauNeRepository;
    private final ConsultationRepository consultationRepository;
    private final UtilisateurRepository utilisateurRepository;

    public DocumentMedicalService(DocumentMedicalRepository repository,
                                  NouveauNeRepository nouveauNeRepository,
                                  ConsultationRepository consultationRepository,
                                  UtilisateurRepository utilisateurRepository) {
        this.repository = repository;
        this.nouveauNeRepository = nouveauNeRepository;
        this.consultationRepository = consultationRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    // CREATE
    public DocumentMedicalResponse create(DocumentMedicalRequest request) {

        NouveauNe bebe = nouveauNeRepository.findById(request.getNouveauNeId())
                .orElseThrow(() -> new RuntimeException("Bébé introuvable"));

        Consultation consultation = null;
        if (request.getConsultationId() != null) {
            consultation = consultationRepository.findById(request.getConsultationId())
                    .orElseThrow(() -> new RuntimeException("Consultation introuvable"));
        }

        Utilisateur uploader = utilisateurRepository.findById(request.getUploaderId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        DocumentMedical doc = DocumentMedical.builder()
                .nomFichier(request.getNomFichier())
                .typeDocument(request.getTypeDocument())
                .cheminFichier(request.getCheminFichier())
                .dateUpload(LocalDateTime.now())
                .nouveauNe(bebe)
                .consultation(consultation)
                .uploader(uploader)
                .build();

        return mapToResponse(repository.save(doc));
    }

    // GET ALL
    public List<DocumentMedicalResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY ID
    public DocumentMedicalResponse getById(Long id) {
        DocumentMedical doc = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document introuvable"));

        return mapToResponse(doc);
    }

    // GET BY BEBE
    public List<DocumentMedicalResponse> getByBebe(Long id) {
        return repository.findByNouveauNeId(id)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY CONSULTATION
    public List<DocumentMedicalResponse> getByConsultation(Long id) {
        return repository.findByConsultationId(id)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    //UPDATE
    public DocumentMedicalResponse update(Long id, DocumentMedicalRequest request) {

        DocumentMedical doc = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document introuvable"));

        NouveauNe bebe = nouveauNeRepository.findById(request.getNouveauNeId())
                .orElseThrow(() -> new RuntimeException("Bébé introuvable"));

        Utilisateur uploader = utilisateurRepository.findById(request.getUploaderId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Consultation consultation = null;
        if (request.getConsultationId() != null) {
            consultation = consultationRepository.findById(request.getConsultationId())
                    .orElseThrow(() -> new RuntimeException("Consultation introuvable"));
        }

        doc.setNomFichier(request.getNomFichier());
        doc.setTypeDocument(request.getTypeDocument());
        doc.setCheminFichier(request.getCheminFichier());
        doc.setNouveauNe(bebe);
        doc.setConsultation(consultation);
        doc.setUploader(uploader);

        return mapToResponse(repository.save(doc));
    }

    // DELETE
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // MAPPER
    private DocumentMedicalResponse mapToResponse(DocumentMedical doc) {

        DocumentMedicalResponse res = new DocumentMedicalResponse();

        res.setId(doc.getId());
        res.setNomFichier(doc.getNomFichier());
        res.setTypeDocument(doc.getTypeDocument());
        res.setCheminFichier(doc.getCheminFichier());
        res.setDateUpload(doc.getDateUpload());

        res.setNouveauNeId(doc.getNouveauNe().getId());

        if (doc.getConsultation() != null) {
            res.setConsultationId(doc.getConsultation().getId());
        }

        res.setUploaderId(doc.getUploader().getId());

        return res;
    }
}