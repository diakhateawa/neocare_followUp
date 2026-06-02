package com.awa.neocare_followUp.dto;

import lombok.Data;

@Data
public class ConsultationRequest {
    private Long nouveauNeId;
    private Long medecinId;
    private Double temperature;
    private Double poids;
    private Double taille;
    private Double pc;
    private String diagnostic;
    private String observations;
    private String modeAlimentation;
    private String assistanceRespiratoire;
    private String resultatsBiologiques;
}

