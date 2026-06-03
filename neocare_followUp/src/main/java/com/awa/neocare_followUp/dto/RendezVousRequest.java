package com.awa.neocare_followUp.dto;

import com.awa.neocare_followUp.entity.StatutRendezVous;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RendezVousRequest {
    private LocalDateTime dateRdv;
    private StatutRendezVous statut;
    private String motif;
    private Long nouveauNeId;
    private Long secretaireId;
}
