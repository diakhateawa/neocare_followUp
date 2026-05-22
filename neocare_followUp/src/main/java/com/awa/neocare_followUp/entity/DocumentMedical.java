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
public class DocumentMedical {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomFichier;

    @Enumerated(EnumType.STRING)
    private TypeDocument typeDocument;

    private String cheminFichier;

    private LocalDateTime dateUpload;

    @ManyToOne
    @JoinColumn(name = "nouveau_ne_id")
    private NouveauNe nouveauNe;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    @ManyToOne
    @JoinColumn(name = "uploader_id")
    private Utilisateur uploader;
}