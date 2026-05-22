package com.awa.neocare_followUp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NouveauNeRequest {

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String sexe;

    private Double poidsNaissance;
    private Double tailleNaissance;
    private Double pcNaissance;

    private Integer ageGestationnel;
    private Integer apgar1Min;
    private Integer apgar5Min;

    private String modeNaissance;

    private Long mereId; //
}