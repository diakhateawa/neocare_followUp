package com.awa.neocare_followUp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateConsultation;

    private Double temperature;
    private Double poids;
    private Double taille;
    private Double pc;

    @Column(length = 1000)
    private String diagnostic;

    @Column(length = 2000)
    private String observations;

    private String modeAlimentation;
    private String assistanceRespiratoire;
    private String resultatsBiologiques;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nouveau_ne_id")
    private NouveauNe nouveauNe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medecin_id")
    private Utilisateur medecin;

    @OneToOne(mappedBy = "consultation", cascade = CascadeType.ALL)
    private Prescription prescription;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL)
    private List<DocumentMedical> documents;
}