package com.awa.neocare_followUp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultationResponse {
    private Long id;
    private LocalDateTime dateConsultation;

    private Double temperature;
    private Double poids;
    private Double taille;   // 👈 OBLIGATOIRE
    private Double pc;

    private String diagnostic;
    private String observations;

    private Long nouveauNeId;
    private String nouveauNeNom;

    private Long medecinId;
    private String medecinNom;
}