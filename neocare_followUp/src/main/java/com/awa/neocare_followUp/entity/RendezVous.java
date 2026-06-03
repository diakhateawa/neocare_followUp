package com.awa.neocare_followUp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "nouveau_ne_id")
    private NouveauNe nouveauNe;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "secretaire_id")
    private Utilisateur secretaire;
}