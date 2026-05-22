package com.awa.neocare_followUp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NouveauNe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Enumerated(EnumType.STRING)
    private StatutNouveauNe statut;

    private LocalDate dateSortie;

    @ManyToOne
    @JoinColumn(name = "mere_id")
    private Mere mere;

    @OneToMany(mappedBy = "nouveauNe")
    private List<Consultation> consultations;

    @OneToMany(mappedBy = "nouveauNe")
    private List<RendezVous> rendezVous;

    @OneToMany(mappedBy = "nouveauNe")
    private List<Alerte> alertes;

    @OneToMany(mappedBy = "nouveauNe")
    private List<DocumentMedical> documents;
}
