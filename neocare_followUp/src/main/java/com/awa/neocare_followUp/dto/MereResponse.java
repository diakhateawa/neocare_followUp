package com.awa.neocare_followUp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MereResponse {

    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String telephone;
    private String adresse;
    private String numeroDossier;
    private String email;
}