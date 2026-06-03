package com.awa.neocare_followUp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "secretaire")
    private List<RendezVous> rendezVous;

    @JsonIgnore
    @OneToMany(mappedBy = "uploader")
    private List<DocumentMedical> documents;

    @JsonIgnore
    @OneToMany(mappedBy = "medecin")
    private List<Alerte> alertes;


}