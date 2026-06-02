package com.awa.neocare_followUp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NouveauNeResponse {

    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String sexe;

    private Double poidsNaissance;
    private Double tailleNaissance;
    private Double pcNaissance;

    private Integer ageGestationnel;

    private String statut;

    private Long mereId;
    private String mereNom;

    private Integer ageCorrige;
}