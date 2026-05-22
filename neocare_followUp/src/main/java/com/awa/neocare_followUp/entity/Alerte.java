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
public class Alerte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String message;

    @Enumerated(EnumType.STRING)
    private TypeAlerte typeAlerte;

    private LocalDateTime dateCreation;

    private boolean traite;

    @ManyToOne
    @JoinColumn(name = "nouveau_ne_id")
    private NouveauNe nouveauNe;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Utilisateur medecin;
}