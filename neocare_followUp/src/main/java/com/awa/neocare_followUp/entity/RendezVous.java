package com.awa.neocare_followUp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateRdv;

    @Enumerated(EnumType.STRING)
    private StatutRendezVous statut;

    private String motif;

    @ManyToOne
    @JoinColumn(name = "nouveau_ne_id")
    private NouveauNe nouveauNe;

    @ManyToOne
    @JoinColumn(name = "secretaire_id")
    private Utilisateur secretaire;
}