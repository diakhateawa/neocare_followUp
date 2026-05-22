package com.awa.neocare_followUp.service;

import com.awa.neocare_followUp.dto.NouveauNeRequest;
import com.awa.neocare_followUp.dto.NouveauNeResponse;
import com.awa.neocare_followUp.entity.Mere;
import com.awa.neocare_followUp.entity.NouveauNe;
import com.awa.neocare_followUp.repository.MereRepository;
import com.awa.neocare_followUp.repository.NouveauNeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NouveauNeService {

    private final NouveauNeRepository nouveauNeRepository;
    private final MereRepository mereRepository;
    private final AgeService ageService;

    public NouveauNeService(NouveauNeRepository repo,
                            MereRepository mereRepository,
                            AgeService ageService) {
        this.nouveauNeRepository = repo;
        this.mereRepository = mereRepository;
        this.ageService = ageService;
    }

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

        // 👉 ici tu peux calculer l'âge corrigé (EXEMPLE)
        int ageReel = 0; // à remplacer plus tard avec logique date
        int ageCorrige = ageService.calculAgeCorrige(
                saved.getAgeGestationnel(),
                ageReel
        );

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

        res.setMereId(mere.getId());
        res.setMereNom(mere.getNom());

        // 👉 optionnel si tu ajoutes champ
        // res.setAgeCorrige(ageCorrige);

        return res;
    }

    public List<NouveauNe> getAll() {
        return nouveauNeRepository.findAll();
    }
}