package com.awa.neocare_followUp.service;

import com.awa.neocare_followUp.dto.MereRequest;
import com.awa.neocare_followUp.dto.MereResponse;
import com.awa.neocare_followUp.entity.Mere;
import com.awa.neocare_followUp.repository.MereRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MereService {

    private final MereRepository mereRepository;

    public MereService(MereRepository mereRepository) {
        this.mereRepository = mereRepository;
    }

    // CREATE
    public MereResponse create(MereRequest request) {

        // Vérification doublon (optionnel mais pro)
        if (mereRepository.findByNumeroDossier(request.getNumeroDossier()).isPresent()) {
            throw new RuntimeException("Numéro dossier déjà existant");
        }

        Mere mere = Mere.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .dateNaissance(request.getDateNaissance())
                .telephone(request.getTelephone())
                .adresse(request.getAdresse())
                .numeroDossier(request.getNumeroDossier())
                .email(request.getEmail())
                .build();

        Mere saved = mereRepository.save(mere);

        return mapToResponse(saved);
    }

    // GET ALL
    public List<MereResponse> getAll() {
        return mereRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY ID
    public MereResponse getById(Long id) {
        Mere mere = mereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mère introuvable"));

        return mapToResponse(mere);
    }

    // UPDATE
    public MereResponse update(Long id, MereRequest request) {

        Mere mere = mereRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mère introuvable"));

        mere.setNom(request.getNom());
        mere.setPrenom(request.getPrenom());
        mere.setDateNaissance(request.getDateNaissance());
        mere.setTelephone(request.getTelephone());
        mere.setAdresse(request.getAdresse());
        mere.setNumeroDossier(request.getNumeroDossier());
        mere.setEmail(request.getEmail());

        Mere updated = mereRepository.save(mere);

        return mapToResponse(updated);
    }

    // DELETE
    public void delete(Long id) {
        if (!mereRepository.existsById(id)) {
            throw new EntityNotFoundException("Mère introuvable");
        }
        mereRepository.deleteById(id);
    }

    // MAPPING
    private MereResponse mapToResponse(Mere mere) {
        MereResponse res = new MereResponse();
        res.setId(mere.getId());
        res.setNom(mere.getNom());
        res.setPrenom(mere.getPrenom());
        res.setDateNaissance(mere.getDateNaissance());
        res.setTelephone(mere.getTelephone());
        res.setAdresse(mere.getAdresse());
        res.setNumeroDossier(mere.getNumeroDossier());
        res.setEmail(mere.getEmail());
        return res;
    }
}