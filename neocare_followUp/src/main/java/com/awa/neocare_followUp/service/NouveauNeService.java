package com.awa.neocare_followUp.service;

import com.awa.neocare_followUp.dto.NouveauNeRequest;
import com.awa.neocare_followUp.dto.NouveauNeResponse;
import com.awa.neocare_followUp.entity.Mere;
import com.awa.neocare_followUp.entity.NouveauNe;
import com.awa.neocare_followUp.repository.MereRepository;
import com.awa.neocare_followUp.repository.NouveauNeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NouveauNeService {

    private final NouveauNeRepository nouveauNeRepository;
    private final MereRepository mereRepository;
    private final AgeService ageService;

    public NouveauNeService(NouveauNeRepository nouveauNeRepository,
                            MereRepository mereRepository,
                            AgeService ageService) {
        this.nouveauNeRepository = nouveauNeRepository;
        this.mereRepository = mereRepository;
        this.ageService = ageService;
    }

    // CREATE
    public NouveauNeResponse create(NouveauNeRequest request) {

        Mere mere = mereRepository.findById(request.getMereId())
                .orElseThrow(() -> new RuntimeException("Mère introuvable"));

        NouveauNe bebe = NouveauNe.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .dateNaissance(request.getDateNaissance())
                .sexe(request.getSexe())
                .poidsNaissance(request.getPoidsNaissance())
                .tailleNaissance(request.getTailleNaissance())
                .pcNaissance(request.getPcNaissance())
                .ageGestationnel(request.getAgeGestationnel())
                .apgar1Min(request.getApgar1Min())
                .apgar5Min(request.getApgar5Min())
                .modeNaissance(request.getModeNaissance())
                .mere(mere)
                .build();

        NouveauNe saved = nouveauNeRepository.save(bebe);

        int ageReel = 0;
        int ageCorrige = ageService.calculAgeCorrige(
                saved.getAgeGestationnel(),
                ageReel
        );

        return mapToResponse(saved, ageCorrige);
    }

    // GET ALL
    public List<NouveauNeResponse> getAll() {
        return nouveauNeRepository.findAll()
                .stream()
                .map(bebe -> {
                    int ageCorrige = ageService.calculAgeCorrige(
                            bebe.getAgeGestationnel(),
                            0
                    );
                    return mapToResponse(bebe, ageCorrige);
                })
                .toList();
    }

    // GET BY ID
    public NouveauNeResponse getById(Long id) {

        NouveauNe bebe = nouveauNeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nouveau-né introuvable"));

        int ageCorrige = ageService.calculAgeCorrige(
                bebe.getAgeGestationnel(),
                0
        );

        return mapToResponse(bebe, ageCorrige);
    }

    // UPDATE
    @Transactional
    public NouveauNeResponse update(Long id, NouveauNeRequest request) {

        NouveauNe bebe = nouveauNeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nouveau-né introuvable"));

        Mere mere = mereRepository.findById(request.getMereId())
                .orElseThrow(() -> new RuntimeException("Mère introuvable"));

        bebe.setNom(request.getNom());
        bebe.setPrenom(request.getPrenom());
        bebe.setDateNaissance(request.getDateNaissance());
        bebe.setSexe(request.getSexe());
        bebe.setPoidsNaissance(request.getPoidsNaissance());
        bebe.setTailleNaissance(request.getTailleNaissance());
        bebe.setPcNaissance(request.getPcNaissance());
        bebe.setAgeGestationnel(request.getAgeGestationnel());
        bebe.setApgar1Min(request.getApgar1Min());
        bebe.setApgar5Min(request.getApgar5Min());
        bebe.setModeNaissance(request.getModeNaissance());
        bebe.setMere(mere);

        NouveauNe updated = nouveauNeRepository.save(bebe);

        int ageCorrige = ageService.calculAgeCorrige(
                updated.getAgeGestationnel(),
                0
        );

        return mapToResponse(updated, ageCorrige);
    }

    // DELETE
    public void delete(Long id) {

        NouveauNe bebe = nouveauNeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nouveau-né introuvable"));

        nouveauNeRepository.delete(bebe);
    }

    // MAPPING CENTRALISÉ
    private NouveauNeResponse mapToResponse(NouveauNe saved, Integer ageCorrige) {

        NouveauNeResponse res = new NouveauNeResponse();

        res.setId(saved.getId());
        res.setNom(saved.getNom());
        res.setPrenom(saved.getPrenom());
        res.setDateNaissance(saved.getDateNaissance());
        res.setSexe(saved.getSexe());
        res.setPoidsNaissance(saved.getPoidsNaissance());
        res.setTailleNaissance(saved.getTailleNaissance());
        res.setPcNaissance(saved.getPcNaissance());
        res.setAgeGestationnel(saved.getAgeGestationnel());
        res.setStatut(saved.getStatut() != null ? saved.getStatut().name() : null);

        res.setMereId(saved.getMere().getId());
        res.setMereNom(saved.getMere().getNom());

        res.setAgeCorrige(ageCorrige);

        return res;
    }
}