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
public class Alerte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private TypeAlerte typeAlerte;

    private LocalDateTime dateCreation;

    private boolean traite = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "nouveau_ne_id")
    private NouveauNe nouveauNe;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Utilisateur medecin;

    @PrePersist
    public void init() {
        this.dateCreation = LocalDateTime.now();
        this.traite = false;
    }
}