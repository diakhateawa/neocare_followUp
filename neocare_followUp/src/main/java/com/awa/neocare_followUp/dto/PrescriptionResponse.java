package com.awa.neocare_followUp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrescriptionResponse {
    private Long id;
    private String medicaments;
    private String posologie;
    private String duree;
    private LocalDateTime datePrescription;

    private Long consultationId;
    private Long nouveauNeId;
    private String nouveauNeNom;

    private Long medecinId;
    private String medecinNom;
}
