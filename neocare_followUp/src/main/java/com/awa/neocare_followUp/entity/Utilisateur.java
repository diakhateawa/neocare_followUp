package com.awa.neocare_followUp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String nom;
    private String prenom;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean actif;

    @OneToMany(mappedBy = "medecin")
    private List<Consultation> consultations;

    @OneToMany(mappedBy = "secretaire")
    private List<RendezVous> rendezVous;

    @OneToMany(mappedBy = "uploader")
    private List<DocumentMedical> documents;

    @OneToMany(mappedBy = "medecin")
    private List<Alerte> alertes;


}