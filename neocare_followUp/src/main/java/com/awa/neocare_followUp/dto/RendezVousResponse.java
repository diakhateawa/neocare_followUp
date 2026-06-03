package com.awa.neocare_followUp.dto;

import com.awa.neocare_followUp.entity.StatutRendezVous;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RendezVousResponse {
    private Long id;

    private LocalDateTime dateRdv;

    private StatutRendezVous statut;

    private String motif;

    private Long nouveauNeId;
    private String nouveauNeNom;

    private Long secretaireId;
    private String secretaireNom;
}