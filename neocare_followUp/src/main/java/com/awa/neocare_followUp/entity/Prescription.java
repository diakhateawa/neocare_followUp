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
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicaments;
    private String posologie;
    private String duree;

    private LocalDateTime datePrescription;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;
}
