package com.awa.neocare_followUp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RendezVousRequest {

    private LocalDateTime dateRdv;

    private String motif;

    private Long nouveauNeId;

    private Long secretaireId;
}