package com.awa.neocare_followUp.entity;

public enum TypeDocument {
    // Imagerie médicale
    RADIOGRAPHIE,
    ECHOGRAPHIE,
    SCANNER,
    IRM,

    // Biologie
    BILAN_BIOLOGIQUE,
    ANALYSE_SANGUINE,
    ANALYSE_URINAIRE,

    // Documents cliniques
    ORDONNANCE,
    COMPTE_RENDU_CONSULTATION,
    COMPTE_RENDU_HOSPITALISATION,

    // Surveillance bébé
    COURBE_POIDS,
    COURBE_CROISSANCE,
    VACCINATION,

    // Autres
    PHOTO,
    AUTRE
}