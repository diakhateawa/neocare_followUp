package com.awa.neocare_followUp.security.service;

import com.awa.neocare_followUp.dto.MereRequest;
import com.awa.neocare_followUp.dto.MereResponse;
import com.awa.neocare_followUp.entity.Mere;
import com.awa.neocare_followUp.repository.MereRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MereService {
    private final MereRepository mereRepository;

    public MereService(MereRepository mereRepository) {
        this.mereRepository = mereRepository;
    }

    public MereResponse create(MereRequest request) {

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

    public List<MereResponse> getAll() {
        return mereRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public MereResponse getById(Long id) {
        Mere mere = mereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mère introuvable"));

        return mapToResponse(mere);
    }

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